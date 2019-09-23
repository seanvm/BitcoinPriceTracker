package ca.vanmulligen.bitcoinpricetracker.exchanges;

import java.util.HashMap;

public class ExchangeInfoDTO {
    public String name;
    public String price;
    public HashMap<String, String> prices = new HashMap<>();
    public String currencyPair;
    public boolean finished = false;
}