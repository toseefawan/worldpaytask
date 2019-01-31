package com.example.toseefahmad.worldpaytask.presenter;

import android.content.Intent;

import com.example.toseefahmad.worldpaytask.PaymentAuthView;
import com.example.toseefahmad.worldpaytask.SettlePaymentActivity;
import com.example.toseefahmad.worldpaytask.model.PaymentRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PaymentAuthPresenter implements AuthPresenter , OnTaskCompleted
{
    PaymentAuthView view;
    OkHttpHandler okHttpHandler;
    Gson gson;

    public final static String BOOK = "Book";
    public final static String CURRENCY = "GBP";
    public final static String TR_REF = "Tr:Ref";
    public final static String PAYMENT_TYPE = "card/plain";
    public String url= "https://access.worldpay.com/payments";

    String PaymentResponse = "{\"outcome\": \"authorized\",\"_links\": {\"payments:cancel\":"+
            "{\"href\": \"https://access.worldpay.com/payments/authorizations/cancellations/eyJrIjoiazNhYjYzMiJ9\"},"+
            "\"payments:settle\": {\"href\": \"https://access.worldpay.com/payments/settlements/full/eyJrIjoiazNhYjYzMiJ9\"},"+
            "\"payments:partialSettle\": {\"href\": \"https://access.worldpay.com/payments/settlements/partials/eyJrIjoiazNhYjYzMiJ9\"},"+
            "\"payments:events\": {\"href\": \"https://access.worldpay.com/payments/events/eyJrIjoiazNhYjYzMiJ9\"},\"curies\": [{"+
            "\"name\": \"payments\",\"href\": \"https://access.worldpay.com/rels/payments/{rel}\",\"templated\": true}]}}";

    public PaymentAuthPresenter(PaymentAuthView view, OkHttpHandler okHttpHandler, Gson gson)
    {
        this.view = view;
        this.okHttpHandler = okHttpHandler;
        okHttpHandler.setListener(this);
        this.gson = gson;
    }

    @Override
    public String validatePaymentForm()
    {
        if (!view.getCardNumber().matches("\\d{16}"))
            return " wrong Card number";

        if (!view.getExpiry().matches("^(1[0-2]|0[1-9])/\\d{2}$"))
            return " wrong Card Expiry";

        if (!view.getCvc().matches("\\d{3}"))
            return " wrong Card CVC";

        return "Ok";
    }

    @Override
    public void authorizePayment()
    {
        view.showProgressBar();
        okHttpHandler.execute(url,gson.toJson(buildPaymentRequest()));
    }


    public PaymentRequest buildPaymentRequest()
    {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setTransactionReference(TR_REF);
        PaymentRequest.Instruction instruction = new PaymentRequest.Instruction();
        PaymentRequest.Value value = new PaymentRequest.Value();
        PaymentRequest.PaymentInstrument paymentInstrument = new PaymentRequest.PaymentInstrument();
        PaymentRequest.CardExpiryDate cardExpiryDate = new PaymentRequest.CardExpiryDate();

        instruction.setDescription(BOOK);
        value.setAmount(Integer.parseInt(view.getAmount()));
        value.setCurrency(CURRENCY);
        instruction.setValue(value);

        cardExpiryDate.setMonth(Integer.parseInt(view.getExpiry().split("/")[0]));
        cardExpiryDate.setYear(Integer.parseInt(view.getExpiry().split("/")[1]));

        paymentInstrument.setCardNumber(view.getCardNumber());
        paymentInstrument.setCardHolderName(view.getCardHolderName());
        paymentInstrument.setCvc(view.getCvc());
        paymentInstrument.setType(PAYMENT_TYPE);
        paymentInstrument.setCardExpiryDate(cardExpiryDate);

        instruction.setPaymentInstrument(paymentInstrument);

        paymentRequest.setInstruction(instruction);

        return paymentRequest;
    }

    @Override
    public void onTaskCompleted(String result)
    {
        view.hideProgressBar();

        Intent myIntent = new Intent(view.getContext(), SettlePaymentActivity.class);
        myIntent.putExtra("amount", view.getAmount());
        myIntent.putExtra("cardNo", view.getCardNumber());
        myIntent.putExtra("name", view.getCardHolderName());
        myIntent.putExtra("cvc", view.getCvc());
        myIntent.putExtra("exp", view.getExpiry());
        myIntent.putExtra("response", PaymentResponse);
        view.getContext().startActivity(myIntent);
    }
}
