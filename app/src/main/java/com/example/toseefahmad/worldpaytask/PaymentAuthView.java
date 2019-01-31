package com.example.toseefahmad.worldpaytask;

public interface PaymentAuthView extends BaseView {
    String getAmount();
    String getExpiry();
    String getCardNumber();
    String getCardHolderName();
    String getCvc();
}
