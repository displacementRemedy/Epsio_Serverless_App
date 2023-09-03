package com.example.demo.service;

import java.util.concurrent.ExecutionException;

public interface SleepAndSumServiceInterface {
    int getSleepAndSum(int num1, int num2) throws ExecutionException, InterruptedException;

    int getActiveProcesses();

    long getRequestCounter();
}
