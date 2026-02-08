package com.oljochum.simplifier_server.glossaries;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TermPair {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalTerm;

    @Column(nullable = false)
    private String replacementTerm;

    @ManyToOne
    @JoinColumn(name = "glossary_id", nullable = false)
    private Glossary glossary;

    public Long getId() {
        return id;
    }

    public String getOriginalTerm() {
        return originalTerm;
    }

    public void setOriginalTerm(String originalTerm) {
        this.originalTerm = originalTerm;
    }

    public String getReplacementTerm() {
        return replacementTerm;
    }

    public void setReplacementTerm(String replacementTerm) {
        this.replacementTerm = replacementTerm;
    }

    public Glossary getGlossary() {
        return glossary;
    }

    public void setGlossary(Glossary glossary) {
        this.glossary = glossary;
    }

    public TermPair(String originalTerm, String replacementTerm) {
        this.originalTerm = originalTerm;
        this.replacementTerm = replacementTerm;
    }

    protected TermPair() {
    }
}
