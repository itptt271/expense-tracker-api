package com.itptt.expense_tracker_api.model;

public class DebtPayment {
    private int id;
    private int debtId;
    private String paymentDate;
    private  int amount;

    public DebtPayment(int id, int debtId, String paymentDate, int amount){
        this.id = id;
        this.debtId = debtId;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public int getId(){
        return id;
    }

    public int getDebtId(){
        return debtId;
    }

    public String getPaymentDate(){
        return paymentDate;
    }

    public int getAmount(){
        return amount;
    }
}
