package com.oljochum.simplifier_server.simplify;

import org.springframework.stereotype.Controller;

@Controller
public class SimplifyController {
    private final SimplifyService simplifyService;
    public SimplifyController(SimplifyService simplifyService) {
        this.simplifyService = simplifyService;
    }

    public String handleSimplipy() {
        return simplifyService.callSimplipy();
    }
}
