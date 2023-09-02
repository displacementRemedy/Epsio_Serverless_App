package com.example.demo.controller;

import com.example.demo.service.SleepAndSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitorController {

    private final SleepAndSumService sleepAndSumService;

    @Autowired
    public MonitorController(SleepAndSumService sleepAndSumService) {
        this.sleepAndSumService = sleepAndSumService;
    }

    @GetMapping(value = "/active_processes")
    public int getActiveProcesses() {
        return sleepAndSumService.getActiveProcesses();
    }

    @GetMapping(value = "request_counter")
    public long getRequestCounter() {
        return sleepAndSumService.getRequestCounter();
    }
}