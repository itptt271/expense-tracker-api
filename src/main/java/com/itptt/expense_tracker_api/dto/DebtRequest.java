package com.itptt.expense_tracker_api.dto;

public class DebtRequest {
    private String creditorName;
    private int amount;
    public DebtRequest(){
    }
    public String getCreditorName(){
        return creditorName;
    }
    public void setCreditorName(String creditorName){
        this.creditorName = creditorName;
    }
    public int getAmount(){
        return amount;
    }
    public void setAmount(int amount){
        this.amount = amount;
    }
}
