package com.example.toseefahmad.worldpaytask.model;

import com.google.gson.annotations.SerializedName;

public class PaymentRequest {
    @SerializedName("transactionReference")
    private String transactionReference;
    @SerializedName("instruction")
    private Instruction instruction;


    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public static class Instruction {
        @SerializedName("description")
        private String description;
        @SerializedName("value")
        private Value value;
        @SerializedName("paymentInstrument")
        private PaymentInstrument paymentInstrument;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        public PaymentInstrument getPaymentInstrument() {
            return paymentInstrument;
        }

        public void setPaymentInstrument(PaymentInstrument paymentInstrument) {
            this.paymentInstrument = paymentInstrument;
        }

    }

    public static class PaymentInstrument
    {
        @SerializedName("cvc")
        private String cvc;
        @SerializedName("type")
        private String type;
        @SerializedName("cardNumber")
        private String cardNumber;
        @SerializedName("cardHolderName")
        private String cardHolderName;
        @SerializedName("cardExpiryDate")
        private CardExpiryDate cardExpiryDate;

        public String getCvc() {
            return cvc;
        }

        public void setCvc(String cvc) {
            this.cvc = cvc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardHolderName() {
            return cardHolderName;
        }

        public void setCardHolderName(String cardHolderName) {
            this.cardHolderName = cardHolderName;
        }

        public CardExpiryDate getCardExpiryDate() {
            return cardExpiryDate;
        }

        public void setCardExpiryDate(CardExpiryDate cardExpiryDate) {
            this.cardExpiryDate = cardExpiryDate;
        }
    }

    public static class CardExpiryDate {
        @SerializedName("month")
        private Integer month;
        @SerializedName("year")
        private Integer year;

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

    }

    public static class Value {
        @SerializedName("currency")
        private String currency;
        @SerializedName("amount")
        private Integer amount;

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }
    }

}
