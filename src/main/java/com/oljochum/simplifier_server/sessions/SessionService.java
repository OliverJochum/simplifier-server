package com.oljochum.simplifier_server.sessions;

import java.util.List;

import com.oljochum.simplifier_server.users.User;

public interface SessionService {
    public Session createSession(String name, User user);
    public Snapshot addSnapshotToSession(Session session, String input, String output);
    public Session getSessionByUserId(Long sessionId, Long userId);
    public List<Session> getSessionsByUserId(Long userId);
    public Session createSessionByUserId(Long userId, String sessionName);
}
