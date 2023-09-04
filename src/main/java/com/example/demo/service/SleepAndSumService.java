package com.example.demo.service;

import com.example.demo.callable.SleepAndSumCallable;
import com.example.demo.callable.SleepAndSumThread;
import com.example.demo.reclamation.ThreadShutdownRunnable;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class SleepAndSumService implements SleepAndSumServiceInterface {

    private static BlockingQueue<FutureTask<Integer>> callables;
    private static BlockingQueue<SleepAndSumThread> threads;
    private int requestCounter = 0;
    private final Object requestLock = new Object();
    private final Logger logger = LoggerFactory.getLogger(SleepAndSumService.class);

    public SleepAndSumService() {
        callables = new LinkedBlockingQueue<>();
        threads = new LinkedBlockingQueue<>();
    }

    /**
     * Create always-running thread
     * Sits on the threads queue, checks for expired (over-time)
     */
    @PostConstruct
    public void init() {
        Thread thread = new Thread(new ThreadShutdownRunnable());
        thread.start();
    }


    /**
     * Increment requestCounter
     * Create FutureTask that will be called from within external thread
     * Check if any threads have a WAITING status
     *  If so, notify
     *  If not, create new thread and start
     * @param num1 int
     * @param num2 int
     * @return int
     */
    @Override
    public int getSleepAndSum(int num1, int num2) throws Exception {
        synchronized (requestLock) {
            requestCounter++;
        }

        FutureTask<Integer> task = new FutureTask<>(new SleepAndSumCallable(num1, num2));
        callables.add(task);

        List<SleepAndSumThread> waitingThreads = threads.stream().filter(thread -> thread.getState().equals(Thread.State.WAITING)).toList();
        if (!waitingThreads.isEmpty()) {
            logger.info("Notifying waiting threads");
            try {
                waitingThreads.forEach(thread -> {
                    synchronized (thread) {
                        thread.notify();
                    }
                });
            } catch (Exception e) {
                logger.debug("Caught: " + e);
            }
        } else {
            SleepAndSumThread thread = new SleepAndSumThread();
            logger.info("Creating new thread with ID " + thread.threadId());
            threads.add(thread);
            thread.start();
        }

        return task.get();
    }


    /**
     * Get alive threads
     * @return int
     */
    @Override
    public synchronized int getActiveProcesses() {
        return threads.size();
    }

    /**
     * Get total number of requests, both open and closed
     * @return long
     */
    @Override
    public synchronized long getRequestCounter() {
        return requestCounter;
    }

    //--------------------------------------------------------
    // Static Getter Methods
    //--------------------------------------------------------

    public static synchronized BlockingQueue<FutureTask<Integer>> getCallables() {
        return callables;
    }

    public static synchronized BlockingQueue<SleepAndSumThread> getThreads() {
        return threads;
    }


}
