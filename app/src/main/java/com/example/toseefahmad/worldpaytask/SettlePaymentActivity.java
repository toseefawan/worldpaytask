package com.example.toseefahmad.worldpaytask;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toseefahmad.worldpaytask.presenter.ApprovePresenter;
import com.example.toseefahmad.worldpaytask.presenter.BasePresenter;
import com.example.toseefahmad.worldpaytask.presenter.OkHttpHandler;
import com.example.toseefahmad.worldpaytask.presenter.PaymentApprovePresenter;
import com.example.toseefahmad.worldpaytask.presenter.PaymentAuthPresenter;

import org.json.JSONException;
import org.json.JSONObject;

public class SettlePaymentActivity extends AppCompatActivity implements PaymentApproveView{
    private ProgressBar progressBar;
    ApprovePresenter paymentPresenter;
    TextView amount;
    TextView cardNo;
    TextView nameOnCard;
    TextView cvc;
    TextView expiryDate;
    Button cancel;
    Button confirm;
    String approveUrl;
    String cancelUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_payment);
        paymentPresenter = new PaymentApprovePresenter(this, new OkHttpHandler());
        Intent intent = getIntent();
        String response = intent.getStringExtra("response");

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            cancelUrl = jsonObject.getJSONObject("_links").getJSONObject("payments:cancel").get("href").toString();
            approveUrl = jsonObject.getJSONObject("_links").getJSONObject("payments:settle").get("href").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        amount = findViewById(R.id.amount);
        amount.setText("£" + intent.getStringExtra("amount"));
        cardNo = findViewById(R.id.txtCreditCardNumber);
        cardNo.setText(intent.getStringExtra("cardNo"));
        nameOnCard = findViewById(R.id.txtNameOnCard);
        nameOnCard.setText(intent.getStringExtra("name"));
        cvc = findViewById(R.id.txtCvc);
        cvc.setText(intent.getStringExtra("cvc"));
        expiryDate = findViewById(R.id.txtExpiryDate);
        expiryDate.setText(intent.getStringExtra("exp"));
        cancel = findViewById(R.id.btnCancel);
        confirm = findViewById(R.id.btnConfirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentPresenter.approvePayment(approveUrl);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentPresenter.approvePayment(cancelUrl);
            }
        });
        initProgressBar();
    }

    private void initProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);
        progressBar.setIndeterminate(true);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Resources.getSystem().getDisplayMetrics().widthPixels,
                250);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addContentView(progressBar, params);
        hideProgressBar();
    }

    @Override
    public Context getContext()
    {
        return this;
    }
    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
