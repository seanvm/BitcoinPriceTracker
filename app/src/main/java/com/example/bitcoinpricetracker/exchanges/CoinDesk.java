package com.example.bitcoinpricetracker.exchanges;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bitcoinpricetracker.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CoinDesk implements IExchange {
    static final String url = "https://api.coindesk.com/v1/bpi/currentprice/CAD.json";
    TextView textElement;

    public void call(String currency, RequestQueue queue, View view, Activity activity){
        textElement = (TextView) activity.findViewById(R.id.coinDeskPrice);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String value = parseResponse(response);

                textElement.setText("CoinDesk Price: "+ value);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
            }
        });
        queue.add(jsObjRequest);
    }

    public void onSuccess(){

    }

    public String parseResponse(JSONObject json){
        try {
            return json.getJSONObject("bpi").getJSONObject("CAD").getString("rate");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }
}
