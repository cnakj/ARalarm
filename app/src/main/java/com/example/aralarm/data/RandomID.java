package com.example.aralarm.data;

import java.util.concurrent.atomic.AtomicInteger;

public class RandomID {
    private static final AtomicInteger counter = new AtomicInteger();

    public static int nextValue() {
        return counter.getAndIncrement();
    }
}