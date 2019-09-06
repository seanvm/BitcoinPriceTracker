package ca.vanmulligen.bitcoinpricetracker;

public interface Callback {
    void onSuccess();
    void onError(String err);
}
