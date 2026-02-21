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
public class WSTF extends Score {

    private ScoreType scoreType = ScoreType.READABILITY;

    @Override
    public ScoreType getScoreType() {
        return scoreType;
    }


    @Override
    public float calculate(String... textArgs) {
        String text = textArgs[0];
        // MS is the percentage of words with three syllables or more
        // SL is the median sentence length, by amount of words
        // IW is the percentage of words containing more than six letters
        // ES is the percentage of words containing only one syllable
        Map<String, Integer> syllableCounts = getSyllableCounts(text);
        List<String> words = getWords(text);

        double MS = getWordPctBySyl(words, syllableCounts, 3, ">=");
        double SL = getMedianSentenceLength(getSentenceLengths(text));
        double IW = getWordPctByWordLength(words, 6);
        double ES = getWordPctBySyl(words, syllableCounts, 1, "==");

        return  Math.round((0.1935 * MS) + (0.1672 * SL) + (0.1297 * IW) - (0.0327 * ES) - 0.875);
    }
    
}
