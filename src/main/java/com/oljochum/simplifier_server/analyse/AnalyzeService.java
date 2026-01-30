package com.oljochum.simplifier_server.analyse;

import java.util.Map;

public interface AnalyzeService {
    public Map<String, Float> getRareWords(String text);
    public Map<String, Integer> getComplexSentencesByThreshold(String text, Integer threshold);
    public Map<String, Integer> getComplexSentencesByOutlier(String text);
}
