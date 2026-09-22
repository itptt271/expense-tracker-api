package com.itptt.expense_tracker_api;

import com.itptt.expense_tracker_api.dto.TransactionRequest;
import com.itptt.expense_tracker_api.manager.DatabaseManager;
import com.itptt.expense_tracker_api.model.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController 
public class TransactionController {
    @GetMapping("/api/transactions")
    public List<Transaction> getAllTransactions(){
        return DatabaseManager.getAllTransactions();
    }
    @PostMapping("/api/transactions")
    public String addTransaction(@RequestBody TransactionRequest request){
        DatabaseManager.insertTransaction(
            request.getDate(),
            request.getType(),
            request.getCategory(),
            request.getAmount()
            );
        return "取引を追加しました。";
    }
}
