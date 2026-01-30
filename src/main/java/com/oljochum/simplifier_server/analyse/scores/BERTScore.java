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
public class BERTScore extends Score implements CtxtRetentionMetric {
    private final WebClient webClient = WebClient.create("http://localhost:8000");

    @Override
    public Float calculate(String candidateText, String referenceText) {
        CtxtRetentionReqDTO req = new CtxtRetentionReqDTO(candidateText, referenceText);
        try {
            String res = webClient.post()
                .uri("/bertscore")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"))
                .block();

            return Float.parseFloat(res);
        } catch (WebClientResponseException e) {
            System.out.println("Status code: " + e.getStatusCode());
            System.out.println("Response body: " + e.getResponseBodyAsString());
            System.out.println("Headers: " + e.getHeaders());
            throw e;
        }
    }
}
