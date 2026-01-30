package com.oljochum.simplifier_server.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oljochum.simplifier_server.analyse.AnalyzeService;
import com.oljochum.simplifier_server.analyse.DLexDBService;
import com.oljochum.simplifier_server.analyse.scores.CtxtRetentionMetric;
import com.oljochum.simplifier_server.analyse.scores.ReadibilityMetric;
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

    // generic lets you define any type which implements interfaces
    private final Map<String, ? extends ReadibilityMetric> readbilityScores;
    private final Map<String, ? extends CtxtRetentionMetric> ctxtRetentionScores;
    
    public AnalyzeAPI(Map<String, ? extends ReadibilityMetric> readbilityScores, Map<String, ? extends CtxtRetentionMetric> ctxtRetentionScores) {
        this.readbilityScores = readbilityScores;
        this.ctxtRetentionScores = ctxtRetentionScores;
    }

    @GetMapping("syllable_count")
    public Integer getSyllableCount(@RequestParam String word) {
        return dLexDBService.querySyllableCount(word);
    }

    @GetMapping("syllable_counts")
    public Map<String, Integer> getSyllableCounts(@RequestParam String text) {
        return dLexDBService.querySyllableCounts(text);
    }
    
    @GetMapping("readability/{score}")
    public Integer getScore(@PathVariable String score, @RequestParam String text) {
        ReadibilityMetric scoreService = readbilityScores.get(score);
        if (scoreService == null) {
            throw new IllegalArgumentException("Unknown type: " + score);
        }
        return scoreService.calculate(text);
    }
    
    @GetMapping("context_retention/{score}")
    public Float getContextRetentionScore(@PathVariable String score, @RequestParam String candidateText, @RequestParam String referenceText) {
        CtxtRetentionMetric scoreService = ctxtRetentionScores.get(score);
        if (scoreService == null) {
            throw new IllegalArgumentException("Unknown type: " + score);
        }
        return scoreService.calculate(candidateText, referenceText);
    }

    @GetMapping("rare_words")
    public Map<String, Float> getRareWords(@RequestParam String text) {
        return analyzeService.getRareWords(text);
    }
    
    @GetMapping("complex_sentences")
    public Map<String, Integer> getComplexSentence(@RequestParam String text, @RequestParam(required = false) Integer threshold) {
        if (threshold != null) {
            return analyzeService.getComplexSentencesByThreshold(text, threshold);
        }
        return analyzeService.getComplexSentencesByOutlier(text);
    }
    
}