package com.example.demo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class SleepAndSumService {

    private ThreadPoolExecutor executor;
    private int requestCounter;
    private static final Object countLock = new Object();

    @PostConstruct
    public void init() {
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setKeepAliveTime(6000, TimeUnit.MILLISECONDS);
        requestCounter = 0;
    }

    public int getSleepAndSum(int num1, int num2) throws ExecutionException, InterruptedException {
        final int number1 = num1, number2 = num2;
        Callable<Integer> callableObj = () -> {
            Thread.sleep(3000);
            return number1 + number2;
        };

        Future<Integer> future = executor.submit(callableObj);

        Integer i = future.get();
        incrementCount();
        return i;
    }

    public synchronized int getActiveProcesses() {
        return executor.getActiveCount();
    }

    public synchronized int getRequestCounter() {
        return requestCounter;
    }

    private void incrementCount() {
        synchronized (countLock) {
            requestCounter++;
        }
    }
}
