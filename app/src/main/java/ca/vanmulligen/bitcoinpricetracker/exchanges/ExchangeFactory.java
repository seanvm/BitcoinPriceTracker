package ca.vanmulligen.bitcoinpricetracker.exchanges;

public class ExchangeFactory {
    public IExchange getExchange(String exchangeType){
        if(exchangeType.equalsIgnoreCase("COINDESK")){
            return new CoinDesk();
        }else if(exchangeType.equalsIgnoreCase("COINBASE")){
            return new Coinbase();
        }
        return null;
    }
}
