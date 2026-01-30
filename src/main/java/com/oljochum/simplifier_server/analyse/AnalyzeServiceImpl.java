package com.oljochum.simplifier_server.analyse;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oljochum.simplifier_server.analyse.scores.FRE;
import com.oljochum.simplifier_server.analyse.scores.ScoreUtils;
import com.oljochum.simplifier_server.utils.FileParseUtils;

import io.github.nianna.api.*;


@Service
public class AnalyzeServiceImpl implements AnalyzeService {
    @Autowired
    private FileParseUtils fileParseUtils;
    
    @Autowired
    private FRE fre;
    
    @Autowired
    private ScoreUtils scoreUtils;
    
    @Autowired
    private DLexDBService dLexDBService;
    
    @Override
    public Map<String, Float> getRareWords(String text) {
        return dLexDBService.queryWordsByNormDocFreq(text, 1.0f);
    }

    @Override
    public Map<String, Integer> getComplexSentencesByThreshold(String text, Integer threshold) {
        Map<String, String> GERMAN_ABBREVIATIONS = fileParseUtils.loadGermanAbbreviations();
        Map<String, Integer> sentencesByFRE = new HashMap<>();
        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(Locale.GERMAN);
        
        for (var e : GERMAN_ABBREVIATIONS.entrySet()) {
            text = text.replace(e.getKey(), e.getValue());
        }

        sentenceIterator.setText(text);

        int start = sentenceIterator.first();
        while (sentenceIterator.next() != BreakIterator.DONE) {
            String sentence = text.substring(start, sentenceIterator.current());
            for (var e : GERMAN_ABBREVIATIONS.entrySet()) {
                sentence = sentence.replace(e.getValue(), e.getKey());
            }
            int score = fre.calculate(sentence);
            if (score <= threshold) {
                sentencesByFRE.put(sentence, score);
            }
            start = sentenceIterator.current();
        }
        return sentencesByFRE;
    }

    @Override
    public Map<String, Integer> getComplexSentencesByOutlier(String text) {
        Map<String, String> GERMAN_ABBREVIATIONS = fileParseUtils.loadGermanAbbreviations();
        Map<String, Integer> sentencesByOutlier = new HashMap<>();
        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(Locale.GERMAN);
        for (var e : GERMAN_ABBREVIATIONS.entrySet()) {
            text = text.replace(e.getKey(), e.getValue());
        }
        sentenceIterator.setText(text);

        int outlier = 0; // TODO: calculate outlier

        int start = sentenceIterator.first();
        while (sentenceIterator.next() != BreakIterator.DONE) {
            String sentence = text.substring(start, sentenceIterator.current());
            for (var e : GERMAN_ABBREVIATIONS.entrySet()) {
                sentence = sentence.replace(e.getValue(), e.getKey());
            }
            int score = fre.calculate(sentence);
            if (score <= outlier) {
                sentencesByOutlier.put(sentence, score);
            }
            start = sentenceIterator.current();
        }
        return sentencesByOutlier;
    }

}
