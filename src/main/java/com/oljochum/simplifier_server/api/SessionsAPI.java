package com.oljochum.simplifier_server.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oljochum.simplifier_server.sessions.Session;
import com.oljochum.simplifier_server.sessions.SessionResDTO;
import com.oljochum.simplifier_server.sessions.SessionService;
import com.oljochum.simplifier_server.sessions.SnapshotResDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/sessions")
public class SessionsAPI {
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
}
