package com.oljochum.simplifier_server.simplify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SimplifyController {
    @Autowired
    private SimplifyService simplifyService;

    public String handleLlamaTest() {
        return simplifyService.llamaTest();
    }

    public String handleSimplifyGenerateText(SimplifyRequestDTO req, @RequestParam(required = false) Long glossaryId) {
        if (glossaryId != null) {
            return simplifyService.callSimplipyGenerateText(req, glossaryId);
        }
        return simplifyService.callSimplipyGenerateText(req);
    }

    public String handleSimplifySentenceSimplify(SimplifyRequestDTO req) {
        return simplifyService.callSimplipySentenceSimplify(req);
    }

    public String handleSimplifySentenceSuggest(SimplifyRequestDTO req) {
        return simplifyService.callSimplipySentenceSuggest(req);
    }

    public String handleSimplifySynonyms(SynonymRequestDTO req) {
        return simplifyService.callSimplipySynonyms(req);
    }
}
