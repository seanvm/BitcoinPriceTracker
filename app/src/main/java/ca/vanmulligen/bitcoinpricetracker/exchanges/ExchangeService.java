package ca.vanmulligen.bitcoinpricetracker.exchanges;

import android.app.Activity;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import ca.vanmulligen.bitcoinpricetracker.Callback;

public class ExchangeService {
    View view;
    Activity activity;
    RequestQueue queue;
    Callback callback; // Runs when all requests have finished
    int requestCounter = 0; // Keeps track of volley requests

    public ExchangeService(View view, Activity _activity, Callback successCallback){
        this.view = view;
        this.activity = _activity;
        this.queue = Volley.newRequestQueue(this.activity);
        this.callback = successCallback;
    }

    public void setValueFromExchange(IExchange exchange, String currency){
        requestCounter++;
        exchange.call(currency, queue, view, this.activity, callback());
    }

    private Callback callback() {
        final Callback cb = this.callback;
        return new Callback() {
            @Override
            public void onSuccess() {
                requestCounter--;
                try{
                    Thread.sleep(200);
                }catch(InterruptedException e){
                    System.out.println("Main thread interrupted");
                }
                if (requestCounter == 0) {
                    cb.onSuccess();
                }
            }

            @Override
            public void onError(String err) {
                System.out.println(err);
            }
        };
    }
}
