package ca.vanmulligen.bitcoinpricetracker;

public interface Callback<T> {
    void onSuccess(T data);
    void onError(String err);
}
