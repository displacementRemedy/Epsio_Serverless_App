package com.example.demo.controller;

import com.example.demo.service.SleepAndSumServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitorController {

    private final SleepAndSumServiceInterface sleepAndSumService;

    @Autowired
    public MonitorController(SleepAndSumServiceInterface sleepAndSumService) {
        this.sleepAndSumService = sleepAndSumService;
    }

    /**
     * GET mapping of /active_processes
     * No parameters
     * @return int
     */
    @GetMapping(value = "/active_processes")
    public int getActiveProcesses() {
        return sleepAndSumService.getActiveProcesses();
    }

    /**
     * GET mapping of /request_counter
     * No parameters
     * @return long
     */
    @GetMapping(value = "request_counter")
    public long getRequestCounter() {
        return sleepAndSumService.getRequestCounter();
    }
}