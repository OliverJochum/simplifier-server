package com.oljochum.simplifier_server.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.oljochum.simplifier_server.analyse.scores.Score.ScoreType;
import com.oljochum.simplifier_server.sessions.Session;
import com.oljochum.simplifier_server.utils.ScoreTypeMapConverter;

import jakarta.persistence.*;

@Entity
@Table(name = "user_accounts")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Session> sessions = new ArrayList<>();

    @Column
    @Convert(converter = ScoreTypeMapConverter.class)
    private Map<String, ScoreType> selectedScores;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, ScoreType> getSelectedScores() {
        return selectedScores;
    }

    public void setSelectedScores(Map<String, ScoreType> selectedScores) {
        this.selectedScores = selectedScores;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected User() {
    }
    
}
