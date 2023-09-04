package com.example.demo.reclamation;

import com.example.demo.service.SleepAndSumService;

public class ThreadShutdownRunnable implements Runnable {
    /**
     * Runnable that sits on a static threads object
     * Checks for termination qualification every 100 milliseconds
     */
    @Override
    public void run() {
        try {
            while (true) {
                SleepAndSumService.getThreads().stream()
                        .filter(Thread::isAlive)
                        .filter(thread -> !thread.isRunning())
                        .filter(thread -> System.currentTimeMillis() > thread.getLastRunInMillis() + 6000)
                        .forEach(thread -> {
                            synchronized (this) {
                                thread.interrupt();
                                thread.stopAlive();
                                SleepAndSumService.getThreads().remove(thread);
                            }
                        });

                //Busy-waiting to sit on threads. Shouldn't wait or notify, who knows when the next request will come in
                Thread.sleep(100);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
