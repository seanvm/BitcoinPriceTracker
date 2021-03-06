package ca.vanmulligen.bitcoinpricetracker.views.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.TreeMap;

import ca.vanmulligen.bitcoinpricetracker.Callback;
import ca.vanmulligen.bitcoinpricetracker.R;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeFactory;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeInfoDTO;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeService;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeSettingDTO;
import ca.vanmulligen.bitcoinpricetracker.exchanges.IExchange;
import ca.vanmulligen.bitcoinpricetracker.views.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {
    public TreeMap<String, ExchangeInfoDTO> dataMap = new TreeMap<>(); // Use TreeMap to for natural ordering of keys
    public RecyclerView.Adapter adapter;
    public RecyclerView recyclerView;
    public SwipeRefreshLayout swipeRefreshLayout;
    public ExchangeService exchangeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildView();
        showLoading();
        populateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this,
                        SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                // Do nothing
        }

        return super.onOptionsItemSelected(item);
    }

    private void buildView(){
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeToRefreshListener();

        recyclerView = findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MainViewAdapter(dataMap);
        recyclerView.setAdapter(adapter);
    }

    private void populateView() {
        clearView();

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);

        this.exchangeService = new ExchangeService(this, onFinishLoad());
        ArrayList<ExchangeSettingDTO> exchanges = exchangeService.getExchangeSettings();

        // Get preferences for each exchange
        exchangeService.setRequestCounter(getNumberOfRequestsToMake(sharedPreferences, exchanges));

        // Get prices if exchange enabled
        getPricesFromExchanges(sharedPreferences, exchanges);
    }

    private int getNumberOfRequestsToMake(SharedPreferences sharedPreferences, ArrayList<ExchangeSettingDTO> exchanges){
        int requestCounter = 0;
        for(int i = 0; i < exchanges.size(); i++){
            boolean preference = sharedPreferences.getBoolean(exchanges.get(i).settingsKey, exchanges.get(i).defaultVal);
            if(preference){
                requestCounter++;
            }
        }

        return requestCounter;
    }

    private void getPricesFromExchanges(SharedPreferences sharedPreferences, ArrayList<ExchangeSettingDTO> exchanges) {
        ExchangeFactory exchangeFactory = new ExchangeFactory();

        for(int i = 0; i < exchanges.size(); i++){
            boolean preference = sharedPreferences.getBoolean(exchanges.get(i).settingsKey, exchanges.get(i).defaultVal);
            if(preference){
                IExchange exchange = exchangeFactory.getExchange(exchanges.get(i).name);
                exchangeService.setValueFromExchange(exchange, exchanges.get(i).currency);
            }
        }
    }

    private void clearView(){
        dataMap.clear();
        adapter = new MainViewAdapter(dataMap);
        recyclerView.setAdapter(adapter);
    }

    private void swipeToRefreshListener(){
        swipeRefreshLayout.setOnRefreshListener(
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    populateView();
                }
            }
        );
    }

    private void hideLoading(){
        swipeRefreshLayout.setRefreshing(false);
    }
    private void showLoading(){
        swipeRefreshLayout.setRefreshing(true);
    }

    private Callback onFinishLoad(){
        return new Callback<ExchangeInfoDTO>() {
            @Override
            public void onSuccess(ExchangeInfoDTO exchangeInfo) {
                recordExchangeValues(exchangeInfo);

                if(exchangeInfo.finished){
                    adapter.notifyDataSetChanged();
                    hideLoading();
                }
            }

            @Override
            public void onError(String err) {
                System.out.println(err);
            }
        };
    }

    private void recordExchangeValues(ExchangeInfoDTO exchangeInfo) {
        if(dataMap.containsKey(exchangeInfo.name)){
            ExchangeInfoDTO dto = dataMap.get(exchangeInfo.name);
            dto.prices.put(exchangeInfo.currencyPair, exchangeInfo.price);
        } else {
            dataMap.put(exchangeInfo.name, exchangeInfo);
        }
    }
}

