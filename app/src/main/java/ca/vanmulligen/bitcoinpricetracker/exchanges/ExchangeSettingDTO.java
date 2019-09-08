package ca.vanmulligen.bitcoinpricetracker.exchanges;

public class ExchangeSettingDTO {
    public String name;
    public String settingsTitle;
    public String settingsKey;
    public String currency;
    public String description;
    public Boolean defaultVal;

    public ExchangeSettingDTO(String name, String settingsTitle, String settingsKey, String currency, String description, Boolean defaultVal) {
        this.name = name;
        this.settingsTitle = settingsTitle;
        this.settingsKey = settingsKey;
        this.currency = currency;
        this.description = description;
        this.defaultVal = defaultVal;
    }
}
