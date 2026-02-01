package com.oljochum.simplifier_server.sessions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oljochum.simplifier_server.users.User;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionRepo sessionRepo;

    @Override
    public Session createSession(String name, User user) {
        return sessionRepo.save(new Session(name, user));
    }

    @Override
    public Snapshot addSnapshotToSession(Session session, String input, String output) {
        Snapshot snapshot = new Snapshot(input, output);
        session.getSnapshots().add(snapshot);
        sessionRepo.save(session);
        return snapshot;
    }

    @Override
    public Session getSessionByUserId(long sessionId, Long userId) {
        return sessionRepo.findByIdAndUserId(sessionId, userId);
    }

    @Override
    public List<Session> getSessionsByUserId(Long userId) {
        return sessionRepo.findByUserId(userId);
    }
    
}
