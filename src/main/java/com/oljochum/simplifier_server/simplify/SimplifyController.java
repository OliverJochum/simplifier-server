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

    public String handleSimplifyGenerateText(SimplifyRequestDTO req) {
        return simplifyService.callSimplipyGenerateText(req);
    }

    public String handleSimplifySentenceSimplify(SimplifyRequestDTO req) {
        return simplifyService.callSimplipySentenceSimplify(req);
    }

    public String handleSimplifySentenceSuggest(SimplifyRequestDTO req) {
        return simplifyService.callSimplipySentenceSuggest(req);
    }
}
