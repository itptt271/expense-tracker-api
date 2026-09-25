package com.itptt.expense_tracker_api.dto;

public class PaymentRequest {
    private int payment;
    public PaymentRequest(){
    }
    public int getPayment(){
        return payment;
    }
    public void setPayment(int payment){
        this.payment = payment;
    }
}
