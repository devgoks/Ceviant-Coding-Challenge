package com.ceviant.coding.challenge.exercise1.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Statistics {

    private BigDecimal max;

    private BigDecimal min;

    private BigDecimal sum;

    private BigDecimal avg;

    private long count;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public Statistics () {
        this.max = BigDecimal.ZERO;
        this.min = BigDecimal.ZERO;
        this.avg = BigDecimal.ZERO;
        this.sum = BigDecimal.ZERO;
        this.count = 0;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void lockForRead() {
        this.lock.readLock().lock();
    }

    public void releaseReadLock() {
        this.lock.readLock().unlock();
    }

    public void lockForWrite() {
        this.lock.writeLock().lock();
    }

    public void releaseWriteLock() {
        this.lock.writeLock().unlock();
    }

    public void addNewTransaction(BigDecimal transactionAmount) {

        this.lockForWrite();

        if (this.count == 0){
            this.min = transactionAmount;
            this.max = transactionAmount;
        }else {
            this.min = transactionAmount.min(this.min);
            this.max = transactionAmount.max(this.max);
        }
        this.sum = this.sum.add(transactionAmount);
        this.count = this.count + 1;
        this.avg = sum.divide(new BigDecimal(count), RoundingMode.HALF_UP);

        this.releaseWriteLock();
    }

    public Map<String, Object> snapshot() {

        Map<String, Object> snapshot = new HashMap<>();

        this.lockForRead();
        snapshot.put("sum", this.sum.toString());
        snapshot.put("avg", this.avg.toString());
        snapshot.put("max", this.max.toString());
        snapshot.put("min", this.min.toString());
        snapshot.put("count", this.count);

        this.releaseReadLock();

        return snapshot;
    }

    public void reset() {

        this.lockForWrite();

        this.max = BigDecimal.ZERO;
        this.min = BigDecimal.ZERO;
        this.avg = BigDecimal.ZERO;
        this.sum = BigDecimal.ZERO;
        this.count = 0;

        this.releaseWriteLock();
    }

}
