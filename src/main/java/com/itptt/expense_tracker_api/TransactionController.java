package com.itptt.expense_tracker_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController 
public class TransactionController {
    @GetMapping("/api/transactions")
    public String getAllTransactions(){
        return "Hello from Transaction API";
    }
}
