package com.example.bitcoinpricetracker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bitcoinpricetracker.exchanges.CoinDesk;
import com.example.bitcoinpricetracker.exchanges.Coinbase;
import com.example.bitcoinpricetracker.exchanges.ExchangeService;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.bitcoinpricetracker.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getBitcoinPrice(null);
    }

    public void getBitcoinPrice(View view) {
        findViewById(R.id.button2).setVisibility(View.GONE);
        findViewById(R.id.progressBar1).setVisibility(View.VISIBLE);

        ExchangeService exchangeService = new ExchangeService(view, this);
        exchangeService.setValueFromExchange(new CoinDesk(), "CAD");
        exchangeService.setValueFromExchange(new Coinbase(), "CAD");
    }
}

