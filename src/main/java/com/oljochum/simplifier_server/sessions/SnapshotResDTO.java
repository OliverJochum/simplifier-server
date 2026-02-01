package com.oljochum.simplifier_server.sessions;

import java.time.LocalDateTime;

public record SnapshotResDTO(LocalDateTime datetime, String input, String output) {
    
}
