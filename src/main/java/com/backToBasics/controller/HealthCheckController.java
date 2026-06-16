package com.backToBasics.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/system")
public class HealthCheckController {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        // We use LinkedHashMap to keep the JSON keys in a specific order
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("name", appName);
        response.put("version", appVersion);
        response.put("status", "UP");
        response.put("timestamp", System.currentTimeMillis());

        return response;
    }
}
