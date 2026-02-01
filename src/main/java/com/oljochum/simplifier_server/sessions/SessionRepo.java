package com.oljochum.simplifier_server.sessions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepo extends JpaRepository<Session, Long> {
    public Session findByIdAndUserId(long sessionId, Long userId);
    public List<Session> findByUserId(Long userId);
}
