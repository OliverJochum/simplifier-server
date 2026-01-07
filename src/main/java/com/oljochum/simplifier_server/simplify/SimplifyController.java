package com.oljochum.simplifier_server.simplify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SimplifyController {
    @Autowired
    private SimplifyService simplifyService;

    public String handleLlamaTest() {
        return simplifyService.llamaTest();
    }

    public String handleSimplipy(SimplifyRequestDTO req) {
        return simplifyService.callSimplipy(req);
    }
}
