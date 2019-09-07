package ca.vanmulligen.bitcoinpricetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ca.vanmulligen.bitcoinpricetracker.exchanges.CoinDesk;
import ca.vanmulligen.bitcoinpricetracker.exchanges.Coinbase;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeInfo;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeService;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "ca.vanmulligen.bitcoinpricetracker.MESSAGE";
    public List<ExchangeInfo> dataList = new ArrayList<>();
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(dataList);
        recyclerView.setAdapter(adapter);

        getBitcoinPrice(null);
    }

    public void getBitcoinPrice(View view) {
        showLoading();

        ExchangeService exchangeService = new ExchangeService(view, this, onFinishLoad());
        exchangeService.setValueFromExchange(new CoinDesk(), "CAD");
        exchangeService.setValueFromExchange(new Coinbase(), "CAD");
    }

    private void showLoading(){
        //        findViewById(R.id.button2).setVisibility(View.GONE);
        //        findViewById(R.id.progressBar1).setVisibility(View.VISIBLE);
    }

    private void hideLoading(){

        //        findViewById(R.id.progressBar1).setVisibility(View.GONE);
        //        findViewById(R.id.button2).setVisibility(View.VISIBLE);
    }

    private Callback onFinishLoad(){
        return new Callback<ExchangeInfo>() {
            @Override
            public void onSuccess(ExchangeInfo exchangeInfo) {
                dataList.add(exchangeInfo);
                Log.d("DataList","Add");
                adapter.notifyDataSetChanged();
                //hideLoading();
            }

            @Override
            public void onError(String err) {
                System.out.println(err);
            }
        };
    }
}

