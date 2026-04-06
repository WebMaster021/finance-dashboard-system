package com.finance.financedashboard.controller;

import com.finance.financedashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;

//    It returns summary such as total balance, total expense and balance.
    @GetMapping
    public Map<String, Object> getSummary(@RequestParam UUID userId) {
        return service.summary(userId);
    }
}
