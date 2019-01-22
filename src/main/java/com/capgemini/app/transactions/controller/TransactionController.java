package com.capgemini.app.transactions.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capgemini.app.transactions.service.TransactionService;
import com.capgemini.app.transactions.transactiontype.Transaction;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.util.ArrayList;




@RestController
@RequestMapping("/transactions")
public class TransactionController {
	@Autowired
	private TransactionService service;
	
	@Autowired
	RestTemplate restTemplate;
	@PostMapping("/deposits")
	public ResponseEntity<Transaction> deposit(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity=restTemplate.getForEntity("http://localhost:8989/accounts/" + transaction.getAccountNumber() + "/balance",
				Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = service.deposit(transaction.getAccountNumber(),transaction.getAmount(), currentBalance,transaction.getTransactionDetails());
		restTemplate.put("http://localhost:8989/accounts/"+transaction.getAccountNumber()+"?currentBalance="+updateBalance, null);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	@PostMapping("/withdraws")
	public ResponseEntity<Transaction> withdraw(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity=restTemplate.getForEntity("http://localhost:8989/accounts/" + transaction.getAccountNumber() + "/balance",
				Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = service.withdraw(transaction.getAccountNumber(),transaction.getAmount(), currentBalance,transaction.getTransactionDetails());
		restTemplate.put("http://localhost:8989/accounts/"+transaction.getAccountNumber()+"?currentBalance="+updateBalance, null);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	@GetMapping("/statements")
	public ResponseEntity<CurrentDataSet> getStatement(){
		CurrentDataSet currentDataSet = new CurrentDataSet();
		List<Transaction> transactions = service.getStatement();
		currentDataSet.setTransactions(transactions);
		return new ResponseEntity<CurrentDataSet>(currentDataSet,HttpStatus.OK);
	}
	
	
	
}
