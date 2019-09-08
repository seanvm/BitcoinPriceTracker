package ca.vanmulligen.bitcoinpricetracker.exchanges;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import ca.vanmulligen.bitcoinpricetracker.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public abstract class BaseExchange implements IExchange {
    private String url;
    String currencyPair = "BTC/CAD";

    protected String getUrl() {
        return url;
    }

    protected void setUrl(String url) {
        this.url = url;
    }

    public void call(String currency, RequestQueue queue, Activity activity, Callback callback){
        final Callback<ExchangeInfoDTO> cb = callback;
        Log.d("hi", this.getUrl());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, this.getUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String price = parseResponse(response);

                cb.onSuccess(buildExchangeInfo(price, currencyPair));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        queue.add(jsObjRequest);
    }

    public abstract String parseResponse(JSONObject json);

    public String formatNumber(String value) {
        NumberFormat format = NumberFormat.getInstance(Locale.CANADA);
        Number number = 0;

        try {
            number = format.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DecimalFormat df = new DecimalFormat("##,##0.00");
        return df.format(number);
    }


    private ExchangeInfoDTO buildExchangeInfo(String price, String currencyPair){
        ExchangeInfoDTO exchangeInfo = new ExchangeInfoDTO();
        exchangeInfo.name = this.getClass().getSimpleName();
        exchangeInfo.price = price;
        exchangeInfo.currencyPair = currencyPair;

        return exchangeInfo;
    }
}