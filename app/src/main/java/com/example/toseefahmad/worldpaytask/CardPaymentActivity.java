package com.example.toseefahmad.worldpaytask;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import com.example.toseefahmad.worldpaytask.presenter.AuthPresenter;
import com.example.toseefahmad.worldpaytask.presenter.OkHttpHandler;
import com.example.toseefahmad.worldpaytask.presenter.PaymentAuthPresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CardPaymentActivity extends AppCompatActivity implements PaymentAuthView {

   private ProgressBar progressBar;
   AuthPresenter paymentPresenter;
   TextView amount;
   EditText cardNo;
   EditText nameOnCard;
   EditText cvc;
   EditText expiryDate;
   Button cancel;
   Button confirm;
   public static String AMOUNT = "200";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paymentPresenter = new PaymentAuthPresenter(this, new OkHttpHandler(),  new GsonBuilder().create());

        amount = findViewById(R.id.textView5);
        amount.setText(AMOUNT);
        cardNo = findViewById(R.id.creditCardNumber);
        nameOnCard = findViewById(R.id.nameOnCard);
        cvc = findViewById(R.id.cvc);
        expiryDate = findViewById(R.id.expiryDate);
        cancel = findViewById(R.id.btnCancel);
        confirm = findViewById(R.id.btnConfirm);
        final View mainLayout = findViewById(R.id.mainLayout);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = paymentPresenter.validatePaymentForm();
                if (msg.contentEquals("Ok"))
                    paymentPresenter.authorizePayment();
                else
                    Snackbar.make(mainLayout,"Form has error : " + msg,Snackbar.LENGTH_LONG).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // go back to previous page
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

    @Override
    public String getAmount()
    {
        return amount.getText().toString();
    }

    @Override
    public String getExpiry()
    {
        return expiryDate.getText().toString();
    }
    @Override
    public String getCardNumber()
    {
        return cardNo.getText().toString();
    }
    @Override
    public String getCardHolderName()
    {
        return nameOnCard.getText().toString();
    }
    @Override
    public String getCvc()
    {
        return cvc.getText().toString();
    }
}
