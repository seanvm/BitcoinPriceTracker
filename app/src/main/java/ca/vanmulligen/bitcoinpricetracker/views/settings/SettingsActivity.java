package ca.vanmulligen.bitcoinpricetracker.views.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ca.vanmulligen.bitcoinpricetracker.R;

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
