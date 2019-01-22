package com.capgemini.app.transactions.service;

import java.time.LocalDate;
import java.util.List;

import com.capgemini.app.transactions.transactiontype.Transaction;

public interface TransactionService {
	List<Transaction> getStatement(LocalDate startDate, LocalDate endDate);
	double deposit(int accountNumber,double amount,double currentBalance,String transactionDetails);
	double withdraw(int accountNumber,double amount,double currentBalance,String transactionDetails);
	List<Transaction> getStatement();

}