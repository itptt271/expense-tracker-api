package com.itptt.expense_tracker_api;

import com.itptt.expense_tracker_api.dto.TransactionRequest;
import com.itptt.expense_tracker_api.manager.DatabaseManager;
import com.itptt.expense_tracker_api.model.Transaction;
import com.itptt.expense_tracker_api.model.Category;
import com.itptt.expense_tracker_api.model.TransactionType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController 
public class TransactionController {
    // GET - 取引一覧を取得
    @GetMapping("/api/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> transactions = DatabaseManager.getAllTransactions();
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }
    // POST - 取引を新規追加
    @PostMapping("/api/transactions")
    public ResponseEntity<String> addTransaction(@RequestBody  TransactionRequest request){
        try {
            validateRequest(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        DatabaseManager.insertTransaction(
            request.getDate(),
            request.getType(),
            request.getCategory(),
            request.getAmount()
            );
        return ResponseEntity.status(HttpStatus.CREATED).body("取引を追加しました。");
    }
    // PUT - 取引を更新（IDで指定）
    @PutMapping("/api/transactions/{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable  int id, @RequestBody TransactionRequest request){
        try {
            validateRequest(request);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        boolean success = DatabaseManager.updateTransaction(
            id,
            request.getDate(),
            request.getType(),
            request.getCategory(),
            request.getAmount()
        );
        if(success){
            return ResponseEntity.status(HttpStatus.OK).body("取引を更新しました。");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("指定されたIDの取引が見つかりません。");
        }
    }
    // DELETE - 取引を削除（IDで指定）
    @DeleteMapping("/api/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable  int id){
        boolean success = DatabaseManager.deleteTransaction(id);
        if(success){
            return ResponseEntity.status(HttpStatus.OK).body("取引を削除しました。");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("指定されたIDの取引が見つかりません。");
        }
    }
    // リクエストデータの検証（POST・PUTで共通利用）
    private  void validateRequest(TransactionRequest request){
        if(request.getAmount() <= 0){
            throw new IllegalArgumentException("金額は0より大きい値を入力してください。");
        }try {
            TransactionType.valueOf(request.getType());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("無効なtypeです: " + request.getType());
        }
        try {
            Category.valueOf(request.getCategory());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("無効なcategoryです: " + request.getCategory());
        }
    }
}
