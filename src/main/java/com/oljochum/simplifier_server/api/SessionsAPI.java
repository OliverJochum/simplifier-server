package com.oljochum.simplifier_server.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oljochum.simplifier_server.sessions.Session;
import com.oljochum.simplifier_server.sessions.SessionReqDTO;
import com.oljochum.simplifier_server.sessions.SessionResDTO;
import com.oljochum.simplifier_server.sessions.SessionService;
import com.oljochum.simplifier_server.sessions.Snapshot;
import com.oljochum.simplifier_server.sessions.SnapshotReqDTO;
import com.oljochum.simplifier_server.sessions.SnapshotResDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/sessions")
public class SessionsAPI {
    private static final Logger logger = LoggerFactory.getLogger(SessionsAPI.class); 

    @Autowired
    private SessionService sessionService;

    @GetMapping("")
    public List<SessionResDTO> getSessionsByUserId(@RequestParam Long userId) {
        List<Session> sessions = sessionService.getSessionsByUserId(userId);
        List<SessionResDTO> sessionDTOs = sessions.stream().map(session -> {
            SnapshotResDTO[] snapshotDTOs = session.getSnapshots().stream()
                    .map(snapshot -> new SnapshotResDTO(snapshot.getDatetime(), snapshot.getInput(), snapshot.getOutput()))
                    .toArray(SnapshotResDTO[]::new);
            return new SessionResDTO(session.getName(), session.getId(), snapshotDTOs);
        }).toList();

        return sessionDTOs;
    }

    @PostMapping("")
    public SessionResDTO createSession(@RequestBody SessionReqDTO sessionReqDTO) {
        Session session = sessionService.createSessionByUserId(sessionReqDTO.userId(), sessionReqDTO.sessionName());
        return new SessionResDTO(session.getName(), session.getId(), new SnapshotResDTO[0]);
    }

    @PostMapping("/{sessionId}/snapshots")
    public SnapshotResDTO addSnapshotToSession(@PathVariable Long sessionId,  @RequestParam Long userId, @RequestBody SnapshotReqDTO snapshotReqDTO) {
        try {
            Session session = sessionService.getSessionByUserId(sessionId, userId);
            Snapshot snapshot = sessionService.addSnapshotToSession(session, snapshotReqDTO.input(), snapshotReqDTO.output());
            return new SnapshotResDTO(snapshot.getDatetime(), snapshot.getInput(), snapshot.getOutput());
        } catch (Exception e) {
            logger.error("Error adding snapshot to session: {}", e.getMessage());
        }
        return null;
    }
}
