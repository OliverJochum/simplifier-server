package com.oljochum.simplifier_server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oljochum.simplifier_server.simplify.SimplifyController;
import com.oljochum.simplifier_server.simplify.SimplifyRequestDTO;
import com.oljochum.simplifier_server.simplify.SynonymRequestDTO;

@RestController
@RequestMapping("/api/simplify/")
public class SimplifyAPI {
    @Autowired
    private SimplifyController simplifyController;

    @PostMapping("generate_text")
    public String postSimplifyGenerateText(@RequestBody SimplifyRequestDTO req) {
        return simplifyController.handleSimplifyGenerateText(req);
    }

    @PostMapping("sentence_simplify")
    public String postSimplifySentenceSimplify(@RequestBody SimplifyRequestDTO req) {
        return simplifyController.handleSimplifySentenceSimplify(req);
    }

    @PostMapping("sentence_suggest")
    public String postSimplifySentenceSuggest(@RequestBody SimplifyRequestDTO req) {
        return simplifyController.handleSimplifySentenceSuggest(req);
    }

    @PostMapping("synonyms")
    public String postSimplifySynonyms(@RequestBody SynonymRequestDTO req) {
        return simplifyController.handleSimplifySynonyms(req);
    }
}
