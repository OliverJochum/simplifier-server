package com.oljochum.simplifier_server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oljochum.simplifier_server.simplify.SimplifyController;
import com.oljochum.simplifier_server.simplify.SimplifyRequestDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class ConnectionTest {
    @Autowired
    private SimplifyController simplifyController;

    @GetMapping("/api/ping")
    public String ping() {
        return "Simplifier API is up!";
    }

    @GetMapping("/api/simplipy")
    public String getLlamaTest() {
        return simplifyController.handleLlamaTest();
    }

    @PostMapping("/api/simplipy/generate_text")
    public String postSimplipyGenerateText(@RequestBody SimplifyRequestDTO req) {
        return simplifyController.handleSimplipyGenerateText(req);
    }

    @PostMapping("/api/simplipy/sentence_simplify")
    public String postSimplipySentenceSimplify(@RequestBody SimplifyRequestDTO req) {
        return simplifyController.handleSimplipySentenceSimplify(req);
    }

    @PostMapping("/api/simplipy/sentence_suggest")
    public String postSimplipySentenceSuggest(@RequestBody SimplifyRequestDTO req) {
        return simplifyController.handleSimplipySentenceSuggest(req);
    }
    
    
}
