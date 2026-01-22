package com.oljochum.simplifier_server.analyse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.nianna.api.*;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFRE'");
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
