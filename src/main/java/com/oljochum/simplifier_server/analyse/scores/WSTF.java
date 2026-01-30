package com.oljochum.simplifier_server.analyse.scores;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * WSTF (Wiener Sachtextformel) Readibility Metric implementation.
 * 
 * Range:
 * Difficulty Grades from 4 to 15 (comparable to German school grades up to 12, after then should be referred to as difficulty levels)
 * 
 */
@Service("wstf")
public class WSTF extends Score implements ReadibilityMetric {
    @Override
    public Integer calculate(String text) {
        // MS is the percentage of words with three syllables or more
        // SL is the median sentence length, by amount of words
        // IW is the percentage of words containing more than six letters
        // ES is the percentage of words containing only one syllable
        Map<String, Integer> syllableCounts = scoreUtils.getSyllableCounts(text);
        List<String> words = scoreUtils.getWords(text);

        double MS = scoreUtils.getWordPctBySyl(words, syllableCounts, 3, ">=");
        double SL = scoreUtils.getMedianSentenceLength(scoreUtils.getSentenceLengths(text));
        double IW = scoreUtils.getWordPctByWordLength(words, 6);
        double ES = scoreUtils.getWordPctBySyl(words, syllableCounts, 1, "==");

        return (int) Math.round((0.1935 * MS) + (0.1672 * SL) + (0.1297 * IW) - (0.0327 * ES) - 0.875);
    }
    
}
