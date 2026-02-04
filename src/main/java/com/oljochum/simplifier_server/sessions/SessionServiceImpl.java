package com.oljochum.simplifier_server.sessions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oljochum.simplifier_server.api.UsersAPI;
import com.oljochum.simplifier_server.users.User;
import com.oljochum.simplifier_server.users.UserService;

@Service
public class SessionServiceImpl implements SessionService {
    private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class); 

    @Autowired
    private UserService userService;

    @Autowired
    private SessionRepo sessionRepo;

    @Override
    public Session createSession(String name, User user) {
        return sessionRepo.save(new Session(name, user));
    }

    @Override
    public Session createSessionByUserId(Long userId, String sessionName) {
        try {
            User user = userService.findById(userId);
            return createSession(sessionName, user);
        } catch (Exception e) {
            throw new IllegalArgumentException("User with id " + userId + " not found.");
        }
    }

    @Override
    public Snapshot addSnapshotToSession(Session session, String input, String output) {
        Snapshot snapshot = new Snapshot(input, output);
        snapshot.setSession(session);
        session.getSnapshots().add(snapshot);
        sessionRepo.save(session);
        return snapshot;
    }

    @Override
    public Session getSessionByUserId(Long sessionId, Long userId) {
    logger.info("Looking for sessionId={}, userId={}", sessionId, userId);
    
    // Step 1: Get by ID only
    Session session = sessionRepo.findById(sessionId)
        .orElseThrow(() -> new IllegalArgumentException("Session " + sessionId + " not found at all"));
    
    logger.info("Found session: id={}, name={}", session.getId(), session.getName());
    
    // Step 2: Check the user
    User user = session.getUser();
    logger.info("Session's user: id={}, username={}", user.getId(), user.getUsername());
    
    // Step 3: Compare user IDs
    if (!user.getId().equals(userId)) {
        throw new IllegalArgumentException(
            "Session " + sessionId + " belongs to user " + user.getId() + 
            ", not user " + userId);
    }
    
    return session;
}

    @Override
    public List<Session> getSessionsByUserId(Long userId) {
        List<Session> sessions = sessionRepo.findByUserId(userId);
        logger.info("userId={}", sessions.get(0).getUser().getId());
        return sessions;
    }
    
}
