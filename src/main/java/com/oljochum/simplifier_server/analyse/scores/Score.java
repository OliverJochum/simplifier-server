package com.oljochum.simplifier_server.analyse.scores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class Score {
    @Autowired
    protected ScoreUtils scoreUtils;
}
