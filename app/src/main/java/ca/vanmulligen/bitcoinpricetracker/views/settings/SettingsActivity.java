package ca.vanmulligen.bitcoinpricetracker.views.settings;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.vanmulligen.bitcoinpricetracker.R;
import ca.vanmulligen.bitcoinpricetracker.exchanges.CoinDesk;
import ca.vanmulligen.bitcoinpricetracker.exchanges.Coinbase;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeInfoDTO;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeService;
import ca.vanmulligen.bitcoinpricetracker.views.main.MainViewAdapter;

public class SettingsActivity extends AppCompatActivity {
    public List<SettingsDTO> dataList = new ArrayList<>();
    public RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buildAndPopulateView();
    }

    private void buildAndPopulateView(){
        setContentView(R.layout.activity_settings);

        RecyclerView recyclerView = findViewById(R.id.settingsCheckboxList);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SettingsViewAdapter(getSettings());
        recyclerView.setAdapter(adapter);
    }

    private List<SettingsDTO> getSettings(){
        List<SettingsDTO> modelList = new ArrayList<SettingsDTO>();
        modelList.add(new SettingsDTO("CoinDesk", true));
        modelList.add(new SettingsDTO("Coinbase", false));

        return modelList;
    }
}
