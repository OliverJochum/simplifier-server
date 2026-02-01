package com.oljochum.simplifier_server.sessions;

public record SessionResDTO(String name, Long id, SnapshotResDTO[] snapshots) {
    
}
