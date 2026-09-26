package com.itptt.expense_tracker_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itptt.expense_tracker_api.dto.DebtRequest;
import com.itptt.expense_tracker_api.dto.PaymentRequest;
import com.itptt.expense_tracker_api.manager.DatabaseManager;
import com.itptt.expense_tracker_api.model.Debt;
import java.util.List;

@RestController 
public class DebtController {
    // GET - 借金一覧を取得
    @GetMapping("/api/debts")
    public ResponseEntity<List<Debt>> getAllDebts(){
        List<Debt> debts = DatabaseManager.getAllDebts();
        return ResponseEntity.status(HttpStatus.OK).body(debts);
    }
    // POST - 新しい借金を追加
    @PostMapping("/api/debts")
    public ResponseEntity<String> addDebt(@RequestBody DebtRequest request){
        if(request.getAmount() <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("金額は0より大きい値を入力してください。");
        }
        DatabaseManager.insertDebt(request.getCreditorName(), request.getAmount(), 0);
        return ResponseEntity.status(HttpStatus.CREATED).body("借金を追加しました。");
    }
    // POST - 返済を記録
    @PostMapping("/api/debts/{id}/payment")
    public ResponseEntity<String> payDebt(@PathVariable int id, @RequestBody PaymentRequest request){
        Debt debt = DatabaseManager.getDebtById(id);
        if(debt == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("指定されたIDの借金が見つかりません。");
        }
        int payment = request.getPayment();
        if(payment <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("返済額は0より大きい値を入力してください。");
        }
        int remaining = debt.getRemainingAmount();
        if(payment > remaining){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("返済額が残りの借金額（" + remaining + "円）を超えています。");
        }
        int newPaidAmount = debt.getPaidAmount() + payment;
        DatabaseManager.updateDebtPayment(id, newPaidAmount);
        return ResponseEntity.status(HttpStatus.OK).body("返済を記録しました。");
    }
    // DELETE - 借金を削除
    @DeleteMapping("/api/debts/{id}")
    public ResponseEntity<String> deleteDebt(@PathVariable int id){
        boolean success = DatabaseManager.deleteDebt(id);
        if(success){
            return ResponseEntity.status(HttpStatus.OK).body("借金を削除しました。");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("指定されたIDの借金が見つかりません。");
        }
    }
}
