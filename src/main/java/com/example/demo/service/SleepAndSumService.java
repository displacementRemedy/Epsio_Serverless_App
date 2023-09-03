package com.example.demo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class SleepAndSumService implements SleepAndSumServiceInterface {

    private ThreadPoolExecutor executor;

    /**
     * Ensure application has started before initializing executor pool
     */
    @PostConstruct
    public void init() {
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setKeepAliveTime(6000, TimeUnit.MILLISECONDS);
    }

    /**
     * Create executable callable, submit to executor. Wait on future get
     *
     * @param num1 int
     * @param num2 int
     * @return int
     * @throws ExecutionException e
     * @throws InterruptedException e
     */
    @Override
    public int getSleepAndSum(int num1, int num2) throws ExecutionException, InterruptedException {
        final int number1 = num1, number2 = num2;
        Callable<Integer> callableObj = () -> {
            Thread.sleep(3000);
            return number1 + number2;
        };

        Future<Integer> future = executor.submit(callableObj);

        return future.get();
    }

    /**
     * Get active threads in executor
     * @return int
     */
    @Override
    public synchronized int getActiveProcesses() {
        return executor.getActiveCount();
    }

    /**
     * Get total threads, both open and closed, in executor
     * @return long
     */
    @Override
    public synchronized long getRequestCounter() {
        return executor.getCompletedTaskCount() + executor.getActiveCount();
    }
}
