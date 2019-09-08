package ca.vanmulligen.bitcoinpricetracker.exchanges;

public class ExchangeSettingDTO {
    public String name;
    public String settingsTitle;
    public String settingsKey;
    public String description;

    public ExchangeSettingDTO(String name, String settingsTitle, String settingsKey, String description) {
        this.name = name;
        this.settingsTitle = settingsTitle;
        this.settingsKey = settingsKey;
        this.description = description;
    }
}
