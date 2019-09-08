package ca.vanmulligen.bitcoinpricetracker.views.settings;

public class SettingsDTO {
    private boolean checked = false;
    private String name = "";

    public SettingsDTO(String name, boolean checked){
        this.name = name;
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String itemText) {
        this.name = itemText;
    }
}
