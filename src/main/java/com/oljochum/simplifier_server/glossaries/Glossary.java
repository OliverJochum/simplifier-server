package com.oljochum.simplifier_server.glossaries;

import java.util.ArrayList;
import java.util.List;

import com.oljochum.simplifier_server.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table (name = "glossaries")
public class Glossary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "glossary", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<TermPair> termPairs = new ArrayList<>();

    

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TermPair> getTermPairs() {
        return termPairs;
    }

    public void setTermPairs(List<TermPair> termPairs) {
        this.termPairs = termPairs;
    }

    public Glossary(String name) {
        this.name = name;
    }

    public Glossary(String name, User user) {
        this.name = name;
        this.user = user;
    }

    protected Glossary() {
    }
}
