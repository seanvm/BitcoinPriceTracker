package ca.vanmulligen.bitcoinpricetracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import ca.vanmulligen.bitcoinpricetracker.exchanges.CoinDesk;
import ca.vanmulligen.bitcoinpricetracker.exchanges.Coinbase;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeService;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "ca.vanmulligen.bitcoinpricetracker.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getBitcoinPrice(null);
    }

    public void getBitcoinPrice(View view) {
        showLoading();

        ExchangeService exchangeService = new ExchangeService(view, this, onFinishLoad());
        exchangeService.setValueFromExchange(new CoinDesk(), "CAD");
        exchangeService.setValueFromExchange(new Coinbase(), "CAD");
    }

    private void showLoading(){
        findViewById(R.id.button2).setVisibility(View.GONE);
        findViewById(R.id.progressBar1).setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        findViewById(R.id.progressBar1).setVisibility(View.GONE);
        findViewById(R.id.button2).setVisibility(View.VISIBLE);
    }

    private Callback onFinishLoad(){
        return new Callback() {
            @Override
            public void onSuccess() {
                hideLoading();
            }

            @Override
            public void onError(String err) {
                System.out.println(err);
            }
        };
    }
}

