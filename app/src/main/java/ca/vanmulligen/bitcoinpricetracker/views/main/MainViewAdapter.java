package ca.vanmulligen.bitcoinpricetracker.views.main;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import ca.vanmulligen.bitcoinpricetracker.R;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeInfoDTO;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.ExchangeViewHolder>{
    private TreeMap<String, ExchangeInfoDTO> exchangeInfoMap;

    MainViewAdapter(TreeMap<String, ExchangeInfoDTO> exchangeInfoMap) {
        this.exchangeInfoMap = exchangeInfoMap;
    }

    @Override
    public int getItemCount() {
        return exchangeInfoMap.size();
    }

    @Override
    public void onBindViewHolder(ExchangeViewHolder exchangeViewHolder, int i) {
        ExchangeInfoDTO ci = (new ArrayList<>(exchangeInfoMap.values())).get(i);

        exchangeViewHolder.vName.setText(ci.name);
        exchangeViewHolder.setPrices(ci);
    }

    @Override
    public ExchangeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // This is only one cardLayout - there can be many
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ExchangeViewHolder(itemView);
    }

    static class ExchangeViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView vName;
        TextView vPrice;
        TextView vCurrencyPair;

        ExchangeViewHolder(View v) {
            super(v);

            view = v;
            vName = v.findViewById(R.id.name);
        }

        void setPrices(ExchangeInfoDTO ci){
            TableLayout tl = view.findViewById(R.id.exchangeTableLayout);

            for (Map.Entry<String, String> entry : ci.prices.entrySet()) {
                Log.d("add price", "add price " + entry.getValue());
                String price = entry.getValue();
                String currencyPair = entry.getKey();

                View priceRow = LayoutInflater.
                        from(view.getContext()).
                        inflate(R.layout.current_price_row, null);

                Log.d("setPrice", currencyPair);
                vCurrencyPair =  priceRow.findViewById(R.id.currencyPair);
                vCurrencyPair.setText((currencyPair));

                vPrice = priceRow.findViewById(R.id.price);
                vPrice.setText((price));

                tl.addView(priceRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }
    }
}

