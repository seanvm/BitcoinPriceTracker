package ca.vanmulligen.bitcoinpricetracker.exchanges;

import org.json.JSONException;
import org.json.JSONObject;

public class Blockchain extends BaseExchange implements IExchange {
    protected String getUrl(String currency){
        return "https://blockchain.info/ticker";
    }

    public String parseResponse(JSONObject json, String currency){
        try {
            String bigNumber = json.getJSONObject(currency).getString("last");
            return formatNumber(bigNumber);

        } catch (JSONException e) {
            return "0";
        }
    }
}