package com.oljochum.simplifier_server.sessions;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Snapshot {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String input;
    
    @Column(nullable = true, columnDefinition = "TEXT")
    private String output;

    public Snapshot(String input, String output) {
        this.datetime = LocalDateTime.now();
        this.input = input;
        this.output = output;
    }

    public Long getId() {
        return id;
    }

    public Session getSession() {
        return session;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
