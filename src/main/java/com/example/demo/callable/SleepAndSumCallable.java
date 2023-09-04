package com.example.demo.callable;

import java.util.concurrent.Callable;

public class SleepAndSumCallable implements Callable<Integer> {

    private final int num1;
    private final int num2;

    public SleepAndSumCallable(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(3000);
        return num1 + num2;
    }
}
