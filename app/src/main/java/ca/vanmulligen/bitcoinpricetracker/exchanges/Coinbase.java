package ca.vanmulligen.bitcoinpricetracker.exchanges;

import org.json.JSONException;
import org.json.JSONObject;

public class Coinbase extends BaseExchange implements IExchange {
    public Coinbase(){
        this.setUrl("https://api.coinbase.com/v2/prices/spot?currency=CAD");
    }

    public String parseResponse(JSONObject json){
        try {
            String bigNumber = json.getJSONObject("data").getString("amount");
            return formatNumber(bigNumber);

        } catch (JSONException e) {
            return "0";
        }
    }
}