package com.example.settlement.ui.sanskarRawat.rv;

public class TransactionModel {
    String payer;
    String payee;
    String amount;
    String description;

    public TransactionModel(String payer, String payee, String amount, String description) {
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
        this.description = description;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
