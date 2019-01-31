package com.example.toseefahmad.worldpaytask;

import com.example.toseefahmad.worldpaytask.model.PaymentRequest;
import com.example.toseefahmad.worldpaytask.presenter.OkHttpHandler;
import com.example.toseefahmad.worldpaytask.presenter.PaymentAuthPresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentAuthPresenterTest {

    PaymentAuthPresenter paymentAuthPresenter;

    @Mock
    PaymentAuthView paymentAuthView;
    @Mock
    PaymentRequest mockedPaymentRequest;
    @Mock
    OkHttpHandler mockedOkHttpHandler;

    Gson gson = new GsonBuilder().create();

    @Before
    public void setup() throws Exception
    {
        paymentAuthPresenter = new PaymentAuthPresenter(paymentAuthView, mockedOkHttpHandler, gson);
    }

    @Test
    public void testPaymentFormIsValid() {

        when(paymentAuthView.getCardNumber()).thenReturn("1234567890123456");
        when(paymentAuthView.getExpiry()).thenReturn("11/20");
        when(paymentAuthView.getCvc()).thenReturn("123");

        String msg = paymentAuthPresenter.validatePaymentForm();

        assertEquals("Form not Valid", "Ok", msg);
    }

    @Test
    public void testPaymentRequestIsCreatedAndValid()
    {
        when(paymentAuthView.getAmount()).thenReturn("200");
        when(paymentAuthView.getExpiry()).thenReturn("4/19");

        when(paymentAuthView.getCardNumber()).thenReturn("1234567890123456");
        when(paymentAuthView.getCardHolderName()).thenReturn("Toseef Ahmad");
        when(paymentAuthView.getCvc()).thenReturn("123");

        PaymentRequest paymentRequest = paymentAuthPresenter.buildPaymentRequest();
        assertNotEquals(paymentRequest, null);
        assertNotEquals(paymentRequest.getInstruction(), null);
        assertNotEquals(paymentRequest.getInstruction().getValue(), null);
        assertNotEquals(paymentRequest.getInstruction().getPaymentInstrument(), null);
        assertNotEquals(paymentRequest.getInstruction().getPaymentInstrument().getCardExpiryDate(), null);

        assertEquals("Transcation Ref is not same", paymentRequest.getTransactionReference(),PaymentAuthPresenter.TR_REF);
        assertEquals("Instruction Desc is not same", paymentRequest.getInstruction().getDescription(),PaymentAuthPresenter.BOOK);
        assertEquals("Instruction Currency is not same", paymentRequest.getInstruction().getValue().getCurrency(),PaymentAuthPresenter.CURRENCY);
        assertEquals("Payment Type is not same", paymentRequest.getInstruction().getPaymentInstrument().getType(),PaymentAuthPresenter.PAYMENT_TYPE);

        assertTrue(paymentRequest.getInstruction().getValue().getAmount() ==  200);
        assertTrue(paymentRequest.getInstruction().getPaymentInstrument().getCardExpiryDate().getMonth() ==  4);
        assertTrue(paymentRequest.getInstruction().getPaymentInstrument().getCardExpiryDate().getYear() ==  19);
        assertEquals("Card Number is not same", paymentRequest.getInstruction().getPaymentInstrument().getCardNumber(),"1234567890123456");
        assertEquals("Name on Card is not same", paymentRequest.getInstruction().getPaymentInstrument().getCardHolderName(),"Toseef Ahmad");
        assertEquals("CVC on Card is not same", paymentRequest.getInstruction().getPaymentInstrument().getCvc(),"123");
    }
    @Test
    public void testAuthorizePayment()
    {
        String url= "https://access.worldpay.com/payments";
        when(paymentAuthView.getAmount()).thenReturn("200");
        when(paymentAuthView.getExpiry()).thenReturn("4/19");

        when(paymentAuthView.getCardNumber()).thenReturn("1234567890123456");
        when(paymentAuthView.getCardHolderName()).thenReturn("Toseef Ahmad");
        when(paymentAuthView.getCvc()).thenReturn("123");

        paymentAuthPresenter.authorizePayment();

        Mockito.verify(mockedOkHttpHandler).execute(url,gson.toJson(paymentAuthPresenter.buildPaymentRequest()));
    }

}
