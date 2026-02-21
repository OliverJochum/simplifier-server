package com.oljochum.simplifier_server.analyse.scores;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * BERTScore implementation that communicates with an external BERTScore service. (See SimpliPy API)
 * 
 * Range:
 * 0.0 - 1.0 (Higher is better - percentage similarity) e.g., 0.85 = 85% similar
 */
@Service("bertscore")
public class BERTScore extends Score {
    private final WebClient webClient = WebClient.create("http://localhost:8000");

    private ScoreType scoreType = ScoreType.CONTEXT_RETENTION;

    private float[] boundaries = {0, 1};

    private String rangeLabel = " (Similarity Percentage)";

    @Override
    public ScoreType getScoreType() {
        return scoreType;
    }

    @Override
    public String getLabel(float value) {
        return rangeLabel;
    }

    @Override
    public float calculate(String... textArgs) {
        String candidateText = textArgs[0];
        String referenceText = textArgs[1];
        CtxtRetentionReqDTO req = new CtxtRetentionReqDTO(candidateText, referenceText, "bertscore");
        try {
            String res = webClient.post()
                .uri("/score")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"))
                .block();
            
            float value = Float.parseFloat(res);
            if (value < boundaries[0]) value = boundaries[0];
            if (value > boundaries[1]) value = boundaries[1];

            return value;
        } catch (WebClientResponseException e) {
            System.out.println("Status code: " + e.getStatusCode());
            System.out.println("Response body: " + e.getResponseBodyAsString());
            System.out.println("Headers: " + e.getHeaders());
            throw e;
        }
    }
}
