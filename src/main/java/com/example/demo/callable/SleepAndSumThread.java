package com.example.demo.callable;

import com.example.demo.service.SleepAndSumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class SleepAndSumThread extends Thread {

    Logger logger = LoggerFactory.getLogger(SleepAndSumThread.class);
    private final AtomicBoolean alive = new AtomicBoolean(true);
    private final AtomicBoolean running = new AtomicBoolean(false);
    long lastRunInMillis = System.currentTimeMillis();


    public SleepAndSumThread() {}

    /**
     * While alive, poll from queue
     * Execute callable - the task.get() at the service level shouldn't care where it's coming from
     * wait - we're letting the stop instruction come from outside
     */
    public void run() {

        try {
            while (alive.get()) {
                running.set(true);
                FutureTask<Integer> futureTask = SleepAndSumService.getCallables().poll();
                if (futureTask != null) {
                    futureTask.run();
                    lastRunInMillis = System.currentTimeMillis();
                }
                running.set(false);
                synchronized(this){
                    wait();
                }
            }
        } catch (InterruptedException e) {
          logger.info("Thread " + Thread.currentThread() + " interrupted & is shutting down.");
        } catch (Exception e) {
            logger.debug("Caught:" + e);
        }
    }

    /**
     * Get lastRunInMillis
     * @return long
     */
    public long getLastRunInMillis() {
        return lastRunInMillis;
    }

    /**
     * set alive status to false
     */
    public void stopAlive() {
        alive.set(false);
    }

    /**
     * return boolean value of running
     * @return Boolean
     */
    public Boolean isRunning() {
        return Boolean.TRUE.equals(running.get());
    }


}
