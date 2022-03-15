package com.ceviant.coding.challenge.exercise1.util;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateUtil {

    private static final long timeToLive = 60000;

    public static boolean withinLastMinute(final long time) {
        final long now = System.currentTimeMillis();
        return (now - time) < timeToLive;
    }

    public static boolean isInFuture(Date date) {
        return new Date().before(date);
    }
}
