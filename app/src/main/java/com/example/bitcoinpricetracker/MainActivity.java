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

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.bitcoinpricetracker.MESSAGE";
    private TextView textElementQuadriga;
    private TextView textElementCoinbase;
    public int numberOfRequestsToMake = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getBitcoinPrice(null);
    }

    public void getBitcoinPrice(View view) {
        findViewById(R.id.button2).setVisibility(View.GONE);
        findViewById(R.id.progressBar1).setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        volleyRequestQueue("https://api.quadrigacx.com/v2/ticker?book=btc_cad", "Quadriga", queue);
        volleyRequestQueue("https://api.coinbase.com/v2/prices/spot?currency=CAD", "Coinbase", queue);

    }

    public void volleyRequestQueue(String url, String exchange, RequestQueue queue) {
        final String exchange_final = exchange;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String lastPriceValue = "";

                switch (exchange_final) {
                    case "Quadriga":
                        try {
                            lastPriceValue = response.getString("last");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        textElementQuadriga = (TextView) findViewById(R.id.quadrigaPrice);
                        textElementQuadriga.setText(exchange_final+" Price: "+lastPriceValue);
                        numberOfRequestsToMake--;
                        break;
                    case "Coinbase":
                        try {
                            lastPriceValue = response.getJSONObject("data").getString("amount");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        textElementCoinbase = (TextView) findViewById(R.id.coinbasePrice);
                        textElementCoinbase.setText(exchange_final+" Price: "+lastPriceValue);
                        numberOfRequestsToMake--;
                        break;
                }

                if (numberOfRequestsToMake == 0) {
                    findViewById(R.id.progressBar1).setVisibility(View.GONE);
                    findViewById(R.id.button2).setVisibility(View.VISIBLE);
                    numberOfRequestsToMake = 2;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
            }
        });
        queue.add(jsObjRequest);
    }
}

