package com.oljochum.simplifier_server.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oljochum.simplifier_server.analyse.AnalyzeService;
import com.oljochum.simplifier_server.analyse.DLexDBService;
import com.oljochum.simplifier_server.analyse.scores.Score;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/analyze/")
public class AnalyzeAPI {
    @Autowired
    private DLexDBService dLexDBService;
    @Autowired
    private AnalyzeService analyzeService;

    private final Map<String, Score> scores;
    
    public AnalyzeAPI(Map<String, Score> scores) {
        this.scores = scores;
    }

    @GetMapping("syllable_count")
    public Integer getSyllableCount(@RequestParam String word) {
        return dLexDBService.querySyllableCount(word);
    }

    @GetMapping("syllable_counts")
    public Map<String, Integer> getSyllableCounts(@RequestParam String text) {
        return dLexDBService.querySyllableCounts(text);
    }
    
    @GetMapping("/{score}")
    public Integer getScore(@PathVariable String score, @RequestParam String text) {
        Score scoreService = scores.get(score);
        if (scoreService == null) {
            throw new IllegalArgumentException("Unknown type: " + score);
        }
        return scoreService.calculate(text);
    }
    
}
