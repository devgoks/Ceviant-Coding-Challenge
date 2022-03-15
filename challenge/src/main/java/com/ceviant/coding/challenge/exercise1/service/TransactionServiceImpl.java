package com.ceviant.coding.challenge.exercise1.service;

import com.ceviant.coding.challenge.exercise1.model.Statistics;
import com.ceviant.coding.challenge.exercise1.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static Statistics statistics = new Statistics();

    @Override
    public void addTransaction(Transaction transaction){
        statistics.addNewTransaction(transaction.getAmount());
    }

    @Override
    public Map<String, Object> getStatistics(){
        return statistics.snapshot();
    }

    @Override
    public void deleteTransactions(){
        statistics.reset();
    }
}
