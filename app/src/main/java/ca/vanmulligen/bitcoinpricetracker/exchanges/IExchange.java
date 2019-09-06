package ca.vanmulligen.bitcoinpricetracker.exchanges;

import android.app.Activity;
import android.view.View;

import com.android.volley.RequestQueue;
import ca.vanmulligen.bitcoinpricetracker.Callback;

import org.json.JSONObject;

public interface IExchange {
    static final String url = "";

    public void call(String currency, RequestQueue queue, View view, Activity activity, Callback callback);
    public String parseResponse(JSONObject json);

}
