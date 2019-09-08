package ca.vanmulligen.bitcoinpricetracker.exchanges;

import org.json.JSONException;
import org.json.JSONObject;

public class Coinbase extends BaseExchange implements IExchange {
    protected String getUrl(String currency){
        return "https://api.coinbase.com/v2/prices/spot?currency=" + currency;
    }

    public String parseResponse(JSONObject json, String currency){
        try {
            String bigNumber = json.getJSONObject("data").getString("amount");
            return formatNumber(bigNumber);

        } catch (JSONException e) {
            return "0";
        }
    }
}