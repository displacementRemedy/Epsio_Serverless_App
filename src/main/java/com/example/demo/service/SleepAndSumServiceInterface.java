package com.example.demo.service;

public interface SleepAndSumServiceInterface {
    int getSleepAndSum(int num1, int num2) throws Exception;

    int getActiveProcesses();

    long getRequestCounter();
}
