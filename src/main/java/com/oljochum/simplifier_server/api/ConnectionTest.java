package com.oljochum.simplifier_server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionTest {
    @GetMapping("/api/ping")
    public String ping() {
        return "Simplifier API is up!";
    }
}
