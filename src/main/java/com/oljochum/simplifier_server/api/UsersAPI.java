package com.oljochum.simplifier_server.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oljochum.simplifier_server.analyse.AnalyzeServiceImpl;
import com.oljochum.simplifier_server.analyse.scores.Score;
import com.oljochum.simplifier_server.users.User;
import com.oljochum.simplifier_server.users.UserDTO;
import com.oljochum.simplifier_server.users.UserResDTO;
import com.oljochum.simplifier_server.users.UserService;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UsersAPI {
    private static final Logger logger = LoggerFactory.getLogger(UsersAPI.class); 
    @Autowired
    private UserService userService;

    @PostMapping("")
    public String registerUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.registerUser(userDTO);
            logger.info("Registered user with ID: " + user.getId());
            return "User registered with ID: " + user.getId();
        } catch (Exception e) {
            logger.error("Error registering user: " + e.getMessage());
            throw new RuntimeException("Error registering user: " + e.getMessage());
        } 
        
    }
    
    @GetMapping("/{id}")
    public UserResDTO getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return new UserResDTO(user.getUsername(), user.getId(), user.getSelectedScores());
    }

    @PutMapping("/{id}/selected_scores")
    public String updateSelectedScores(@PathVariable Long id, @RequestBody Map<String, Score.ScoreType> selectedScores) {
        try {
            userService.updateSelectedScores(id, selectedScores);
            logger.info("Updated selected scores for user with ID: " + id);
            return "Selected scores updated for user with ID: " + id;
        } catch (Exception e) {
            logger.error("Error updating selected scores for user with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Error updating selected scores for user with ID " + id + ": " + e.getMessage());
        }
    }
    
}
