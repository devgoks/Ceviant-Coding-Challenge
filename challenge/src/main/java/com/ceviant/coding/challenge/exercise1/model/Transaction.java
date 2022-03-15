package com.ceviant.coding.challenge.exercise1.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    @NotNull
    private BigDecimal amount;
    @NotNull
    @DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss.sssZ")
    private Date timestamp;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
