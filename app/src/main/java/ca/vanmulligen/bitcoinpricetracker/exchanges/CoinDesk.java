package ca.vanmulligen.bitcoinpricetracker.exchanges;

import org.json.JSONException;
import org.json.JSONObject;

public class CoinDesk extends BaseExchange implements IExchange {
    protected String getUrl(String currency){
        return "https://api.coindesk.com/v1/bpi/currentprice/" + currency + ".json";
    }

    public String parseResponse(JSONObject json, String currency){
        try {
            String bigNumber = json.getJSONObject("bpi").getJSONObject(currency).getString("rate");
            return formatNumber(bigNumber);

        } catch (JSONException e) {
            return "0";
        }
    }
}
