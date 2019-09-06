package com.example.bitcoinpricetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.bitcoinpricetracker.exchanges.CoinDesk;
import com.example.bitcoinpricetracker.exchanges.Coinbase;
import com.example.bitcoinpricetracker.exchanges.ExchangeService;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.bitcoinpricetracker.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getBitcoinPrice(null);
    }

    public void getBitcoinPrice(View view) {
        showLoading();

        ExchangeService exchangeService = new ExchangeService(view, this, onFinishLoad());
        exchangeService.setValueFromExchange(new CoinDesk(), "CAD");
        exchangeService.setValueFromExchange(new Coinbase(), "CAD");
    }

    private void showLoading(){
        findViewById(R.id.button2).setVisibility(View.GONE);
        findViewById(R.id.progressBar1).setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        findViewById(R.id.progressBar1).setVisibility(View.GONE);
        findViewById(R.id.button2).setVisibility(View.VISIBLE);
    }

    private Callback onFinishLoad(){
        return new Callback() {
            @Override
            public void onSuccess() {
                hideLoading();
            }

            @Override
            public void onError(String err) {
                System.out.println(err);
            }
        };
    }
}

