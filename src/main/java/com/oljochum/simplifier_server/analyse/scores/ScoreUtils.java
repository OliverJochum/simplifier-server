package com.oljochum.simplifier_server.analyse.scores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oljochum.simplifier_server.analyse.DLexDBService;

import io.github.nianna.api.HyphenatedText;
import io.github.nianna.api.Hyphenator;

@Service
public class ScoreUtils {
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
            totalSyllables += syllableCounts.getOrDefault(word, 0);
        }
        return totalSyllables;
    }

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
