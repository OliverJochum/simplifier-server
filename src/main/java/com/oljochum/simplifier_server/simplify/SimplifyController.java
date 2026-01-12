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

    public String handleSimplipyGenerateText(SimplifyRequestDTO req) {
        return simplifyService.callSimplipyGenerateText(req);
    }

    public String handleSimplipySentenceSimplify(SimplifyRequestDTO req) {
        return simplifyService.callSimplipySentenceSimplify(req);
    }

    public String handleSimplipySentenceSuggest(SimplifyRequestDTO req) {
        return simplifyService.callSimplipySentenceSuggest(req);
    }
}
