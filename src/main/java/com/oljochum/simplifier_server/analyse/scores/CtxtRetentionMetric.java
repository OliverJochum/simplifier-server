package com.oljochum.simplifier_server.analyse.scores;

public interface CtxtRetentionMetric {
    public Float calculate(String candidateText, String referenceText);
}
