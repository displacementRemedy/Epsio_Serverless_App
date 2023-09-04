package com.example.demo.controller;

import com.example.demo.service.SleepAndSumServiceInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class SleepAndSumController {

    private final SleepAndSumServiceInterface sleepAndSumService;

    public SleepAndSumController(SleepAndSumServiceInterface sleepAndSumService) {
        this.sleepAndSumService = sleepAndSumService;
    }

    /**
     * GET mapping of /sleep_and_sum
     * @param num1 int
     * @param num2 int
     * @return int
     */
    @GetMapping(value = "/sleep_and_sum")
    public int getSleepAndSum(int num1, int num2) throws Exception {
        return sleepAndSumService.getSleepAndSum(num1, num2);
    }
}
