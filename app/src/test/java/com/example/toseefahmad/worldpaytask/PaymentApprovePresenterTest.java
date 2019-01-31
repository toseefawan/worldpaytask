package com.example.toseefahmad.worldpaytask;

import com.example.toseefahmad.worldpaytask.presenter.OkHttpHandler;
import com.example.toseefahmad.worldpaytask.presenter.PaymentApprovePresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PaymentApprovePresenterTest {

    PaymentApprovePresenter paymentApprovePresenter;

    @Mock
    PaymentApproveView paymentApproveView;

    @Mock
    OkHttpHandler mockedOkHttpHandler;

    @Before
    public void setup() throws Exception
    {
        paymentApprovePresenter = new PaymentApprovePresenter(paymentApproveView, mockedOkHttpHandler);
    }

    @Test
    public void testApprovePayment()
    {
        String url= "https://access.worldpay.com/payments";
        paymentApprovePresenter.approvePayment(url);
        Mockito.verify(mockedOkHttpHandler).execute(url);
    }

}
