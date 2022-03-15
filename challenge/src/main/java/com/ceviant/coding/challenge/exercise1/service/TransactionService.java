package com.ceviant.coding.challenge.exercise1.service;

import com.ceviant.coding.challenge.exercise1.model.Transaction;

import java.util.Map;

public interface TransactionService {

    public void addTransaction(Transaction transaction);

    public Map<String, Object> getStatistics();

    public void deleteTransactions();
}
