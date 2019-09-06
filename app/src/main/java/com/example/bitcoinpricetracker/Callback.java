package com.example.bitcoinpricetracker;

public interface Callback {
    void onSuccess();
    void onError(String err);
}
