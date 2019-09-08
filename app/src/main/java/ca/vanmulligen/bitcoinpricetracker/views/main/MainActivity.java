package ca.vanmulligen.bitcoinpricetracker.views.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.vanmulligen.bitcoinpricetracker.Callback;
import ca.vanmulligen.bitcoinpricetracker.R;
import ca.vanmulligen.bitcoinpricetracker.exchanges.CoinDesk;
import ca.vanmulligen.bitcoinpricetracker.exchanges.Coinbase;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeFactory;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeInfoDTO;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeService;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeSettingDTO;
import ca.vanmulligen.bitcoinpricetracker.exchanges.IExchange;
import ca.vanmulligen.bitcoinpricetracker.views.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {
    public List<ExchangeInfoDTO> dataList = new ArrayList<>();
    public RecyclerView.Adapter adapter;
    public SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildView();
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

        RecyclerView recyclerView = findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MainViewAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }

    private void populateView() {
        clearView();

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);

        ExchangeFactory exchangeFactory = new ExchangeFactory();
        ExchangeService exchangeService = new ExchangeService(this, onFinishLoad());
        ArrayList<ExchangeSettingDTO> exchanges = exchangeService.getExchangeSettings();

        // Get preferences for each exchange, get prices if exchange enabled
        for(int i = 0; i < exchanges.size(); i++){
            boolean preference = sharedPreferences.getBoolean(exchanges.get(i).settingsKey, exchanges.get(i).defaultVal);
            if(preference){
                IExchange exchange = exchangeFactory.getExchange(exchanges.get(i).name);
                exchangeService.setValueFromExchange(exchange, exchanges.get(i).currency);
            }
        }
    }

    private void clearView(){
        dataList.clear();
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

    private Callback onFinishLoad(){
        return new Callback<ExchangeInfoDTO>() {
            @Override
            public void onSuccess(ExchangeInfoDTO exchangeInfo) {
                dataList.add(exchangeInfo);

                Collections.sort(dataList, new Comparator<ExchangeInfoDTO>() {
                    @Override
                    public int compare(ExchangeInfoDTO e1, ExchangeInfoDTO e2) {
                        return e1.name.compareTo(e2.name);
                    }
                });

                Log.d("DataList","Add");
                adapter.notifyDataSetChanged();
                hideLoading();
            }

            @Override
            public void onError(String err) {
                System.out.println(err);
            }
        };
    }
}

