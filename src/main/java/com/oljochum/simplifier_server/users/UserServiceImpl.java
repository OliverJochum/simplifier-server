package com.oljochum.simplifier_server.users;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.oljochum.simplifier_server.analyse.scores.Score;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.username());
        user.setPassword(passwordEncoder.encode(userDTO.password()));
        try {     
            return userRepo.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error registering user: " + e.getMessage());
        }
    }

    @Override
    public User findById(long id) {
        return userRepo.findById(id);
    }
    
    @Override
    public void updateSelectedScores(Long id, Map<String, Score.ScoreType> selectedScores) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User with ID " + id + " not found"));
        user.setSelectedScores(selectedScores);
        userRepo.save(user);
    }
}
