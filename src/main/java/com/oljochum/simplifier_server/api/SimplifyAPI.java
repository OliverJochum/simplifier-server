package com.oljochum.simplifier_server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oljochum.simplifier_server.simplify.SimplifyController;
import com.oljochum.simplifier_server.simplify.SimplifyRequestDTO;

@RestController
public class SimplifyAPI {
    @Autowired
    private SimplifyController simplifyController;

    @PostMapping("/api/simplify/generate_text")
    public String postSimplifyGenerateText(@RequestBody SimplifyRequestDTO req) {
        return simplifyController.handleSimplifyGenerateText(req);
    }

    @PostMapping("/api/simplify/sentence_simplify")
    public String postSimplifySentenceSimplify(@RequestBody SimplifyRequestDTO req) {
        return simplifyController.handleSimplifySentenceSimplify(req);
    }

    @PostMapping("/api/simplify/sentence_suggest")
    public String postSimplifySentenceSuggest(@RequestBody SimplifyRequestDTO req) {
        return simplifyController.handleSimplifySentenceSuggest(req);
    }
}
