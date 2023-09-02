package com.example.demo.controller;

import com.example.demo.service.SleepAndSumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class SleepAndSumController {

    private final SleepAndSumService sleepAndSumService;

    public SleepAndSumController(SleepAndSumService sleepAndSumService) {
        this.sleepAndSumService = sleepAndSumService;
    }

    @GetMapping(value = "/sleep_and_sum")
    public int getSleepAndSum(int num1, int num2) throws ExecutionException, InterruptedException {
        return sleepAndSumService.getSleepAndSum(num1, num2);
    }
}
