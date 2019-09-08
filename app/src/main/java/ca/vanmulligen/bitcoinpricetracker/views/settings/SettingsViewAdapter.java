package ca.vanmulligen.bitcoinpricetracker.views.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.vanmulligen.bitcoinpricetracker.R;

public class SettingsViewAdapter extends RecyclerView.Adapter<SettingsViewAdapter.SettingsViewHolder>{
    private List<SettingsDTO> settingsList;

    public SettingsViewAdapter(List<SettingsDTO> settingsList) {
        this.settingsList = settingsList;
    }

    @Override
    public int getItemCount() {
        return settingsList.size();
    }

    @Override
    public void onBindViewHolder(SettingsViewHolder settingsViewHolder, int i) {
        SettingsDTO setting = settingsList.get(i);
        settingsViewHolder.vName.setText(setting.getName());
        settingsViewHolder.vChecked.setChecked(setting.isChecked());

        final int pos = i;

        settingsViewHolder.vChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (settingsList.get(pos).isChecked()) {
                    settingsList.get(pos).setChecked(false);
                } else {
                    settingsList.get(pos).setChecked(true);
                }
            }
        });
    }

    @Override
    public SettingsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.setting_list_checkbox, viewGroup, false);

        return new SettingsViewHolder(itemView);
    }

    public static class SettingsViewHolder extends RecyclerView.ViewHolder {
        public TextView vName;
        public CheckBox vChecked;

        public SettingsViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.name);
            vChecked =  (CheckBox) v.findViewById(R.id.list_view_item_checkbox);

            vChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // TODO: Persist settings
                    }
                }
            });
        }
    }
}


