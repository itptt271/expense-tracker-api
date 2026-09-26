package com.itptt.expense_tracker_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.itptt.expense_tracker_api.manager.DatabaseManager;

@SpringBootApplication
public class ExpenseTrackerApiApplication {

	public static void main(String[] args) {
		DatabaseManager.createTransactionsTable();
		DatabaseManager.createDebtsTable();
		DatabaseManager.createDebtPaymentsTable();
		SpringApplication.run(ExpenseTrackerApiApplication.class, args);
	}

}
