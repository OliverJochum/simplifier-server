package com.oljochum.simplifier_server.sessions;

import java.util.ArrayList;
import java.util.List;

import com.oljochum.simplifier_server.users.User;

import jakarta.persistence.*;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Snapshot> snapshots = new ArrayList<>();

    public Session(String name, User user) {
        this.name = name;
        this.user = user;  
    }

    public List<Snapshot> getSnapshots() {
        return snapshots;
    }

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

    public void setSnapshots(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }
    
}
