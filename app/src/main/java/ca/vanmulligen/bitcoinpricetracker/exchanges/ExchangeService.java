package ca.vanmulligen.bitcoinpricetracker.exchanges;

import android.app.Activity;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import ca.vanmulligen.bitcoinpricetracker.Callback;


public class ExchangeService {
    private Activity activity;
    private RequestQueue queue;
    private Callback<ExchangeInfoDTO> callback;

    public ExchangeService(Activity _activity, Callback<ExchangeInfoDTO> successCallback){
        this.activity = _activity;
        this.queue = Volley.newRequestQueue(this.activity);
        this.callback = successCallback;
    }

    public ExchangeService(){}

    public void setValueFromExchange(IExchange exchange, String currency){
        exchange.call(currency, queue, this.activity, callback());
    }

    public ArrayList<ExchangeSettingDTO> getExchangeSettings(){
        ArrayList<ExchangeSettingDTO> exchanges = new ArrayList();
        exchanges.add(new ExchangeSettingDTO("Coinbase", "Coinbase BTC/CAD", "exchanges.coinbase", "CAD", "Enable rates for Coinbase", true));
        exchanges.add(new ExchangeSettingDTO("CoinDesk", "CoinDesk BTC/CAD", "exchanges.coindesk", "CAD","Enable rates for CoinDesk", true));
        exchanges.add(new ExchangeSettingDTO("Blockchain", "Blockchain BTC/USD", "exchanges.blockchain", "USD","Enable rates for Blockchain", false));

        return exchanges;
    }

    private Callback callback() {
        final Callback <ExchangeInfoDTO>cb = this.callback;
        return new Callback<ExchangeInfoDTO>() {
            @Override
            public void onSuccess(ExchangeInfoDTO data) {
                Log.d("Callback","Success");
                cb.onSuccess(data);
            }

            @Override
            public void onError(String err) {
                System.out.println(err);
            }
        };
    }
}
