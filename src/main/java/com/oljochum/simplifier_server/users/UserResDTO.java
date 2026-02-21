package com.oljochum.simplifier_server.users;

import java.util.Map;

import com.oljochum.simplifier_server.analyse.scores.Score.ScoreType;

public record UserResDTO(String username, Long id, Map<String, ScoreType> selectedScores) {
    
}
