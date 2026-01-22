package com.oljochum.simplifier_server.analyse;

import java.util.Map;

public interface AnalyzeService {
    public Integer getFRE(String text);
    public Integer getWSTF(String text);
    public Map<String, Integer> hyphenateText(String text);
}
