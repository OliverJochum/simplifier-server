package com.oljochum.simplifier_server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // generic lets you define any type which implements interfaces
    private final Map<String, ? extends Score> scores;
    
    public AnalyzeAPI(Map<String, ? extends Score> scores) {
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
    
    @GetMapping("readability/{score}")
    public Float getScore(@PathVariable String score, @RequestParam String text) {
        Score scoreService = scores.get(score);
        if (scoreService == null) {
            throw new IllegalArgumentException("Unknown type: " + score);
        }
        return scoreService.calculate(text);
    }
    
    @GetMapping("context_retention/{score}")
    public Float getContextRetentionScore(@PathVariable String score, @RequestParam String candidateText, @RequestParam String referenceText) {
        Score scoreService = scores.get(score);
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
    public Map<String, Float> getComplexSentence(@RequestParam String text, @RequestParam(required = false) Integer threshold) {
        if (threshold != null) {
            return analyzeService.getComplexSentencesByThreshold(text, threshold);
        }
        return analyzeService.getComplexSentencesByOutlier(text);
    }

    @GetMapping("available_scores")
    public Map<String, Score.ScoreType> getAvailableScores() {
        return scores.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getScoreType()));
    }
}