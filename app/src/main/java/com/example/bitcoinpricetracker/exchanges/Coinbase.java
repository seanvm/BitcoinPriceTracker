package com.example.bitcoinpricetracker.exchanges;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bitcoinpricetracker.Callback;
import com.example.bitcoinpricetracker.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Coinbase implements IExchange {
    static final String url = "https://api.coinbase.com/v2/prices/spot?currency=CAD";
    TextView textElement;

    public void call(String currency, RequestQueue queue, View view, Activity activity, Callback callback){
        textElement = (TextView) activity.findViewById(R.id.coinbasePrice);
        final Callback cb = callback;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String value = parseResponse(response);

                textElement.setText("Coinbase Price: "+ value);
                cb.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        queue.add(jsObjRequest);
    }

    public String parseResponse(JSONObject json){
        try {
            return json.getJSONObject("data").getString("amount");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }
}