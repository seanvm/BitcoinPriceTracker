package com.example.bitcoinpricetracker.exchanges;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.bitcoinpricetracker.R;


public class ExchangeService {
    View view;
    Activity activity;
    RequestQueue queue;

    public ExchangeService(View view, Activity _activity){
        this.view = view;
        this.activity = _activity;
        this.queue = Volley.newRequestQueue(this.activity);
    }

    public void setValueFromExchange(IExchange exchange, String currency){
        exchange.call(currency, queue, view, this.activity);
    }
//
//    public void volleyRequestQueue(String url, String exchange, RequestQueue queue) {
//        final String exchange_final = exchange;
//
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                String lastPriceValue = "";
//
//                switch (exchange_final) {
//                    case "CoinDesk":
//                        try {
//                            Log.d("Test",response.toString());
//                            lastPriceValue = response.getJSONObject("bpi").getJSONObject("CAD").getString("rate");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        textElementCoinDesk = (TextView) findViewById(R.id.coinDeskPrice);
//                        textElementCoinDesk.setText(exchange_final+" Price: "+lastPriceValue);
//                        numberOfRequestsToMake--;
//                        break;
//                    case "Coinbase":
//                        try {
//                            lastPriceValue = response.getJSONObject("data").getString("amount");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        textElementCoinbase = (TextView) findViewById(R.id.coinbasePrice);
//                        textElementCoinbase.setText(exchange_final+" Price: "+lastPriceValue);
//                        numberOfRequestsToMake--;
//                        break;
//                }
//
//                if (numberOfRequestsToMake == 0) {
//                    findViewById(R.id.progressBar1).setVisibility(View.GONE);
//                    findViewById(R.id.button2).setVisibility(View.VISIBLE);
//                    numberOfRequestsToMake = 2;
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // TODO Auto-generated method stub
//            }
//        });
//        queue.add(jsObjRequest);
//    }

}
