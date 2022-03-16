package com.ceviant.coding.challenge.exercise1.jobs;

import com.ceviant.coding.challenge.exercise1.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TransactionStatisticsCleanerJob {
    @Autowired
    private TransactionService transactionService;

    @Scheduled(initialDelay = 60000L, fixedDelay = 60000L)
    public void cleanUpTransactions(){
        transactionService.deleteTransactions();
    }
}
