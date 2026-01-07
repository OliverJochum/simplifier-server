package com.oljochum.simplifier_server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oljochum.simplifier_server.simplify.SimplifyController;


@RestController
public class ConnectionTest {
    private final SimplifyController simplifyController;
    
    public ConnectionTest(SimplifyController simplifyController) {
        this.simplifyController = simplifyController;
    }

    @GetMapping("/api/ping")
    public String ping() {
        return "Simplifier API is up!";
    }

    @GetMapping("/api/simplipy")
    public String getSimplipy() {
        return simplifyController.handleSimplipy();
    }
    
}
