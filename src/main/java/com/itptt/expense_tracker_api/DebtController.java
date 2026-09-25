package com.itptt.expense_tracker_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
