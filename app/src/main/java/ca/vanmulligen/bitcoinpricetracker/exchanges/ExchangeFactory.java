package ca.vanmulligen.bitcoinpricetracker.exchanges;

public class ExchangeFactory {
    public IExchange getExchange(String exchangeType){
        switch(exchangeType.toUpperCase()) {
            case "COINDESK":
                return new CoinDesk();
            case "COINBASE":
                return new Coinbase();
            case "BLOCKCHAIN":
                return new Blockchain();
            default:
                return null;
        }
    }
}
