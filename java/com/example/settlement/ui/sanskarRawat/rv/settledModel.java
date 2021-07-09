package com.example.settlement.ui.sanskarRawat.rv;

public class settledModel {
     String payer1;
     String payee1;
     String amount1;

    public settledModel(String payer1, String payee1, String amount1) {
        this.payer1 = payer1;
        this.payee1 = payee1;
        this.amount1 = amount1;
    }

    public String getPayer1() {
        return payer1;
    }

    public void setPayer1(String payer1) {
        this.payer1 = payer1;
    }

    public String getPayee1() {
        return payee1;
    }

    public void setPayee1(String payee1) {
        this.payee1 = payee1;
    }

    public String getAmount1() {
        return amount1;
    }

    public void setAmount1(String amount1) {
        this.amount1 = amount1;
    }
}
