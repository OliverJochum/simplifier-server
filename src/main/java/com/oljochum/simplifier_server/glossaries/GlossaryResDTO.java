package com.oljochum.simplifier_server.glossaries;

public record GlossaryResDTO(Long id, String name, TermPairResDTO[] termPairs) {
    
}
