package com.oljochum.simplifier_server.analyse.scores;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oljochum.simplifier_server.analyse.AnalyzeServiceImpl;

/**
 * Flesch Reading Ease (FRE) metric implementation for German texts.
 * The FRE score is calculated using the formula:
 * FRE = 180 - ASL - (58.5 * ASW)
 * where ASL is the average sentence length (number of words per sentence)
 * and ASW is the average number of syllables per word.
 * Range of scores:
 * 90-100: Very easy
 * 80-89: Easy
 * 70-79: Fairly easy
 * 60-69: Standard
 * 50-59: Fairly difficult
 * 30-49: Difficult
 * 0-29: Very difficult
 */
@Service("fre")
public class FRE extends Score implements ReadibilityMetric {
    private static final Logger logger = LoggerFactory.getLogger(AnalyzeServiceImpl.class); 

    @Override
    public Integer calculate(String text) {
        Map<String, Integer> syllableCounts = scoreUtils.getSyllableCounts(text);
        List<String> words = scoreUtils.getWords(text);
        int sentenceCount = scoreUtils.getSentenceCount(text);
        int totalSyllables = scoreUtils.getTotalSyllables(syllableCounts, words);

        double ASL = (double) words.size() / sentenceCount;
        double ASW = (double) totalSyllables / words.size();

        logger.info("FRE Calculation: sentences={}, words={}, syllables={}, ASL={}, ASW={}", sentenceCount, words.size(), totalSyllables, ASL, ASW);

       return (int) Math.round(180 - ASL - (58.5 * ASW));
    }
}