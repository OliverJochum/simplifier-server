package com.oljochum.simplifier_server.analyse.scores;

public interface ReadibilityMetric {
    public Integer calculate(String text);
}
