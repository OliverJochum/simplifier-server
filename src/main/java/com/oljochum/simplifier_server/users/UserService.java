package com.oljochum.simplifier_server.users;

import java.util.List;
import java.util.Map;

import com.oljochum.simplifier_server.analyse.scores.Score;

public interface UserService {
    public User registerUser(UserDTO userDTO);
    public User findById(long id);
    public void updateSelectedScores(Long id, Map<String, Score.ScoreType> selectedScores);
}
