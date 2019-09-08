package ca.vanmulligen.bitcoinpricetracker.views.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.vanmulligen.bitcoinpricetracker.R;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeInfoDTO;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.ExchangeViewHolder>{
    private List<ExchangeInfoDTO> exchangeInfoList;

    public MainViewAdapter(List<ExchangeInfoDTO> exchangeInfoList) {
        this.exchangeInfoList = exchangeInfoList;
    }

    @Override
    public int getItemCount() {
        return exchangeInfoList.size();
    }

    @Override
    public void onBindViewHolder(ExchangeViewHolder exchangeViewHolder, int i) {
        ExchangeInfoDTO ci = exchangeInfoList.get(i);
        exchangeViewHolder.vName.setText(ci.name);
        exchangeViewHolder.vPrice.setText(ci.price);
        exchangeViewHolder.vCurrencyPair.setText(ci.currencyPair);
    }

    @Override
    public ExchangeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ExchangeViewHolder(itemView);
    }

    public static class ExchangeViewHolder extends RecyclerView.ViewHolder {
        public TextView vName;
        public TextView vPrice;
        public TextView vCurrencyPair;

        public ExchangeViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.name);
            vPrice =  (TextView) v.findViewById(R.id.price);
            vCurrencyPair =  (TextView) v.findViewById(R.id.currencyPair);
        }
    }
}

