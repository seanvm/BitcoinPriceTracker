package ca.vanmulligen.bitcoinpricetracker.exchanges;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import ca.vanmulligen.bitcoinpricetracker.Callback;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class BaseExchange implements IExchange {
    private String url;
    private String currencyPair = "";

    protected String getUrl(String currency) {
        return url;
    }

    public void call(String currency, RequestQueue queue, Activity activity, Callback callback){
        final Callback<ExchangeInfoDTO> cb = callback;
        currencyPair = "BTC/" + currency;
        final String currencyToParse = currency;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, this.getUrl(currency), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String price = parseResponse(response, currencyToParse);

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

    public abstract String parseResponse(JSONObject json, String currency);

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

        if(!exchangeInfo.prices.containsKey(currencyPair)){
            exchangeInfo.prices.put(currencyPair, price);
        }

        logExchangeInfo(exchangeInfo);

        return exchangeInfo;
    }

    private void logExchangeInfo(ExchangeInfoDTO exchangeInfo){
        Map<String, String> map = exchangeInfo.prices;
        List<String> keys = new ArrayList<>(map.keySet());

        for (String key: keys) {
            System.out.println(key + ": " + map.get(key));
        }
    }
}