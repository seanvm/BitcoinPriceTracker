package ca.vanmulligen.bitcoinpricetracker.exchanges;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import ca.vanmulligen.bitcoinpricetracker.Callback;
import ca.vanmulligen.bitcoinpricetracker.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CoinDesk implements IExchange {
    private final String url = "https://api.coindesk.com/v1/bpi/currentprice/CAD.json";
    private TextView textElement;

    public void call(String currency, RequestQueue queue, View view, Activity activity, Callback callback){
        textElement = activity.findViewById(R.id.coinDeskPrice);
        final Callback cb = callback;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String value = parseResponse(response);

                textElement.setText(value);
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
            String bigNumber = json.getJSONObject("bpi").getJSONObject("CAD").getString("rate");
            NumberFormat format = NumberFormat.getInstance(Locale.CANADA);
            Number number = 0;

            try {
                number = format.parse(bigNumber);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DecimalFormat df = new DecimalFormat("##,##0.00");
            return df.format(number).toString();

        } catch (JSONException e) {
           return "0";
        }
    }
}
