package com.itptt.expense_tracker_api.model;

public class Debt {
    private int id;
    private String creditorName;
    private int amount;
    private int paidAmount;
    private String borrowedDate;
    // ファイル読み込み用
    // 保存した借金データを復元するときに使用
    public Debt(int id, String creditorName, int amount, int paidAmount, String borrowedDate){
        this.id = id;
        this.creditorName = creditorName;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.borrowedDate = borrowedDate;
    }
    public int getId(){
        return id;
    }
    public String getCreditorName(){
        return creditorName;
    }
    public int getAmount(){
        return amount;
    }
    public int getPaidAmount(){
        return paidAmount;
    }
    public String getBorrowedDate(){
        return borrowedDate;
    }
    public double getProgressPercentage(){
        return (double) paidAmount / amount * 100;
    }
    public int getRemainingAmount(){
        return amount - paidAmount;
    }
    public boolean isPaid(){
        return paidAmount >= amount;
    }
}