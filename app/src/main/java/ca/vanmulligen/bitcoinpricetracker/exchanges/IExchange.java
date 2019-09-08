package ca.vanmulligen.bitcoinpricetracker.exchanges;

import android.app.Activity;
import android.view.View;

import com.android.volley.RequestQueue;
import ca.vanmulligen.bitcoinpricetracker.Callback;

import org.json.JSONObject;

public interface IExchange {
    String url = "";

    void call(String currency, RequestQueue queue, Activity activity, Callback callback);
    String parseResponse(JSONObject json, String currency);

}
