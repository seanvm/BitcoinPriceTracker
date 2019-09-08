package ca.vanmulligen.bitcoinpricetracker.exchanges;

import org.json.JSONException;
import org.json.JSONObject;

public class CoinDesk extends BaseExchange implements IExchange {
    public CoinDesk(){
        this.setUrl("https://api.coindesk.com/v1/bpi/currentprice/CAD.json");
    }

    public String parseResponse(JSONObject json){
        try {
            String bigNumber = json.getJSONObject("bpi").getJSONObject("CAD").getString("rate");
            return formatNumber(bigNumber);

        } catch (JSONException e) {
            return "0";
        }
    }
}
