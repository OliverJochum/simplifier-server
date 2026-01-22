package com.oljochum.simplifier_server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oljochum.simplifier_server.analyse.DLexDBService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/analyze/")
public class AnalyzeAPI {
    @Autowired
    private DLexDBService dLexDBService;

    @GetMapping("syllable_count")
    public Integer getSyllableCount(@RequestParam String word) {
        return dLexDBService.querySyllableCount(word);
    }
    
}
