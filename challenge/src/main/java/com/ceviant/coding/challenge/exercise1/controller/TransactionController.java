package com.ceviant.coding.challenge.exercise1.controller;

import com.ceviant.coding.challenge.exercise1.model.Transaction;
import com.ceviant.coding.challenge.exercise1.service.TransactionService;
import com.ceviant.coding.challenge.exercise1.util.DateUtil;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity addTransaction(@RequestBody @Valid Transaction transaction) {
        if (DateUtil.isInFuture(transaction.getTimestamp())) {
            return new ResponseEntity(UNPROCESSABLE_ENTITY);
        } else if (DateUtil.withinLastMinute(transaction.getTimestamp().getTime())) {
            transactionService.addTransaction(transaction);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE }, path = "/statistics")
    public ResponseEntity<Map<String,Object>> getStatistics() {
        return ResponseEntity.ok(transactionService.getStatistics());
    }

    @DeleteMapping("/transactions")
    public ResponseEntity deleteTransactions() {
        transactionService.deleteTransactions();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(value = UNPROCESSABLE_ENTITY)
    public void messageNotReadableException(InvalidFormatException exception) {
    }
}
