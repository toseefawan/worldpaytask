package com.example.toseefahmad.worldpaytask.presenter;

import android.app.Activity;
import android.content.Intent;

import com.example.toseefahmad.worldpaytask.PaymentApproveView;
import com.example.toseefahmad.worldpaytask.SettledPaymentActivity;

public class PaymentApprovePresenter implements ApprovePresenter, OnTaskCompleted{

    PaymentApproveView view;
    OkHttpHandler okHttpHandler;

    public PaymentApprovePresenter(PaymentApproveView view, OkHttpHandler okHttpHandler)
    {
        this.view = view;
        this.okHttpHandler= okHttpHandler;
        okHttpHandler.setListener(this);
    }

    @Override
    public void approvePayment(String url)
    {
        view.showProgressBar();
        okHttpHandler.execute(url);
    }
    @Override
    public void onTaskCompleted(String result)
    {
        view.hideProgressBar();
        Intent myIntent = new Intent(view.getContext(), SettledPaymentActivity.class);
        view.getContext().startActivity(myIntent);
        ((Activity)view.getContext()).finish();

    }
}
