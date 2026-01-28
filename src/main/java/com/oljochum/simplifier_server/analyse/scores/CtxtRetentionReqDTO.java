package com.oljochum.simplifier_server.analyse.scores;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CtxtRetentionReqDTO(@JsonProperty("candidate_text") String candidateText, @JsonProperty("reference_text") String referenceText) {
    
}
