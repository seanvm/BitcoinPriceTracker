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
    private int requestCounter = 0;

    public ExchangeService(Activity _activity, Callback<ExchangeInfoDTO> successCallback){
        this.activity = _activity;
        this.queue = Volley.newRequestQueue(this.activity);
        this.callback = successCallback;
    }

    public ExchangeService(){}

    public void setRequestCounter(int count){
        this.requestCounter = count;
    }

    public void setValueFromExchange(IExchange exchange, String currency){
        exchange.call(currency, queue, this.activity, callback());
    }

    public ArrayList<ExchangeSettingDTO> getExchangeSettings(){
        ArrayList<ExchangeSettingDTO> exchanges = new ArrayList();
        exchanges.add(new ExchangeSettingDTO("Coinbase", "Coinbase BTC/CAD", "exchanges.coinbase_cad", "CAD", "Enable rates for Coinbase", true));
        exchanges.add(new ExchangeSettingDTO("Coinbase", "Coinbase BTC/USD", "exchanges.coinbase_usd", "USD", "Enable rates for Coinbase", false));
        exchanges.add(new ExchangeSettingDTO("CoinDesk", "CoinDesk BTC/CAD", "exchanges.coindesk_cad", "CAD","Enable rates for CoinDesk", true));
        exchanges.add(new ExchangeSettingDTO("CoinDesk", "CoinDesk BTC/USD", "exchanges.coindesk_usd", "USD","Enable rates for CoinDesk", false));
        exchanges.add(new ExchangeSettingDTO("Blockchain", "Blockchain BTC/USD", "exchanges.blockchain_usd", "USD","Enable rates for Blockchain", false));
        exchanges.add(new ExchangeSettingDTO("Blockchain", "Blockchain BTC/CAD", "exchanges.blockchain_cad", "CAD","Enable rates for Blockchain", true));

        return exchanges;
    }

    private Callback callback() {
        final Callback <ExchangeInfoDTO>cb = this.callback;
        return new Callback<ExchangeInfoDTO>() {
            @Override
            public void onSuccess(ExchangeInfoDTO data) {
                Log.d("CallbackCount",Integer.toString(requestCounter));
                requestCounter--;
                if(requestCounter == 0){
                    data.finished = true;
                }

                cb.onSuccess(data);
            }

            @Override
            public void onError(String err) {
                System.out.println(err);
            }
        };
    }
}
