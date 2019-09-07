package ca.vanmulligen.bitcoinpricetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ca.vanmulligen.bitcoinpricetracker.exchanges.CoinDesk;
import ca.vanmulligen.bitcoinpricetracker.exchanges.Coinbase;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeInfo;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeService;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "ca.vanmulligen.bitcoinpricetracker.MESSAGE";
    public List<ExchangeInfo> dataList = new ArrayList<>();
    public RecyclerView.Adapter adapter;
    public SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildView();
        populateView();
    }

    private void buildView(){
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeToRefreshListener();

        RecyclerView recyclerView = findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }

    private void populateView() {
        clearView();

        ExchangeService exchangeService = new ExchangeService(this, onFinishLoad());
        exchangeService.setValueFromExchange(new CoinDesk(), "CAD");
        exchangeService.setValueFromExchange(new Coinbase(), "CAD");
    }

    private void clearView(){
        dataList.clear();
    }

    private void swipeToRefreshListener(){
        swipeRefreshLayout.setOnRefreshListener(
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    populateView();
                }
            }
        );
    }

    private void hideLoading(){
        swipeRefreshLayout.setRefreshing(false);
    }

    private Callback onFinishLoad(){
        return new Callback<ExchangeInfo>() {
            @Override
            public void onSuccess(ExchangeInfo exchangeInfo) {
                dataList.add(exchangeInfo);
                Log.d("DataList","Add");
                adapter.notifyDataSetChanged();
                hideLoading();
            }

            @Override
            public void onError(String err) {
                System.out.println(err);
            }
        };
    }
}

