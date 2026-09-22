package com.itptt.expense_tracker_api.dto;

public class TransactionRequest {
    private String date;
    private String type;
    private String category;
    private int amount;
    // Jacksonがリクエストボディをオブジェクトに変換するために必要（引数なしコンストラクタ）
    public TransactionRequest(){
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public int getAmount(){
        return amount;
    }
    public  void setAmount(int amount){
        this.amount = amount;
    }
}
