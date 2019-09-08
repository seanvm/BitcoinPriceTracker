package ca.vanmulligen.bitcoinpricetracker.views.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import java.util.ArrayList;

import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeService;
import ca.vanmulligen.bitcoinpricetracker.exchanges.ExchangeSettingDTO;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = getPreferenceManager().getContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);


        ExchangeService exchangeService = new ExchangeService();
        ArrayList<ExchangeSettingDTO> exchangeSettings = exchangeService.getExchangeSettings();

        for(int i = 0; i < exchangeSettings.size(); i++) {
            addPreference(context, screen, exchangeSettings.get(i));
        }

        setPreferenceScreen(screen);
    }

    private void addPreference(Context context, PreferenceScreen screen, ExchangeSettingDTO setting){
        Preference preference = new CheckBoxPreference(context);

        preference.setKey(setting.settingsKey);
        preference.setTitle(setting.settingsTitle);
        preference.setSummary(setting.description);
        preference.setDefaultValue(setting.defaultVal);

        screen.addPreference(preference);
    }
}
