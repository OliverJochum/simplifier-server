package com.oljochum.simplifier_server.analyse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.nianna.api.*;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {
    private static final Logger logger = LoggerFactory.getLogger(AnalyzeServiceImpl.class); 

    @Autowired
    private DLexDBService dLexDBService;

    private List<String> hyphenationPatternsDE = loadHyphenationPatterns();

    public List<String> loadHyphenationPatterns() {
        try (Stream<String> stream = Files.lines(Path.of("/Users/U462343/bachelors_thesis/simplifier-server/src/main/resources/hypenation_patterns/hyph-de-1996.pat.txt"))) {
            hyphenationPatternsDE = stream.toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hyphenationPatternsDE;
    }

    @Override
    public Integer getFRE(String text) {
        Map<String, Integer> syllableCounts = getSyllableCounts(text);
        List<String> words = getWords(text);
        int sentenceCount = getSentenceCount(text);
        int totalSyllables = getTotalSyllables(syllableCounts, words);

        double ASL = (double) words.size() / sentenceCount;
        double ASW = (double) totalSyllables / words.size();

        logger.info("FRE Calculation: sentences={}, words={}, syllables={}, ASL={}, ASW={}", sentenceCount, words.size(), totalSyllables, ASL, ASW);

       return (int) Math.round(180 - ASL - (58.5 * ASW));
    }

    @Override
    public Integer getWSTF(String text) {
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

        return (int) Math.round((0.1935 * MS) + (0.1672 * SL) + (0.1297 * IW) - (0.0327 * ES) - 0.875);
    }

    public Map<String, Integer> getSyllableCounts(String text) {
        Map<String, Integer> syllableCounts = dLexDBService.querySyllableCounts(text);
        List<String> words = getWords(text);

        List<String> missing = words.stream().filter(s -> !syllableCounts.containsKey(s)).toList();
        Map<String, Integer> missingSyllableCounts = hyphenateText(String.join(" ", missing));

        missingSyllableCounts.forEach((k, v) -> syllableCounts.putIfAbsent(k, v));
        return syllableCounts;
    }

    public double getMedianSentenceLength(List<Integer> sentenceLengths) {
        int size = sentenceLengths.size();
        if (size == 0) return 0;

        List<Integer> sortedLengths = sentenceLengths.stream().sorted().toList();
        if (size % 2 == 1) {
            return sortedLengths.get(size / 2);
        } else {
            return (sortedLengths.get(size / 2 - 1) + sortedLengths.get(size / 2)) / 2.0;
        }
    }

    public int getSentenceCount(String text) {
        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(Locale.GERMAN);
        sentenceIterator.setText(text);

        int sentenceCount = 0;
        int start = sentenceIterator.first();
        while (sentenceIterator.next() != BreakIterator.DONE) {
            sentenceCount++;
        }
        return sentenceCount;
    }

    public List<Integer> getSentenceLengths(String text) {
        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance(Locale.GERMAN);
        sentenceIterator.setText(text);

        List<Integer> sentenceLengths = new java.util.ArrayList<>();
        int start = sentenceIterator.first();

        while (sentenceIterator.next() != BreakIterator.DONE) {
            String sentence = text.substring(start, sentenceIterator.current());
            int wordCount = getWords(sentence).size();
            sentenceLengths.add(wordCount);
        }
        return sentenceLengths;
    }

    public List<String> getWords(String text) {
        return List.of(text.split("\\P{L}+"));
    }

    public double getWordPctBySyl(List<String> words, Map<String, Integer> syllableCounts, int syllableThreshold, String sign) {
        long count = 0;
        if (sign == ">=") {
            count = words.stream().filter(word -> syllableCounts.getOrDefault(word, 0) >= syllableThreshold).count();
            
        } else if (sign == "==") {
            count = words.stream().filter(word -> syllableCounts.getOrDefault(word, 0) == syllableThreshold).count();
        }
        else {
            throw new IllegalArgumentException("Invalid sign: " + sign);
        }
        return (double) count * 100 / words.size();
    }

    public double getWordPctByWordLength(List<String> words, int lengthThreshold) {
        long count = words.stream().filter(word -> word.length() > lengthThreshold).count();
        return (double) count * 100 / words.size();
    }

    public int getTotalSyllables(Map<String, Integer> syllableCounts, List<String> words) {
        int totalSyllables = 0;
        for (String word : words) {
            totalSyllables += syllableCounts.getOrDefault(word, 0); // add 0 if key doesn't exist
        }
        return totalSyllables;
    }

    @Override
    public Map<String, Integer> hyphenateText(String text) {
        Hyphenator hyphenator = new Hyphenator(hyphenationPatternsDE);
        HyphenatedText hyphenatedText = hyphenator.hyphenateText(text);
        Map<String, Integer> hyphenationMap = new HashMap<>();

        hyphenatedText.hyphenatedTokens().forEach( token -> {
            hyphenationMap.put(token.token(), token.hyphenIndexes().size() + 1);
        });

        return hyphenationMap;
    }
}
