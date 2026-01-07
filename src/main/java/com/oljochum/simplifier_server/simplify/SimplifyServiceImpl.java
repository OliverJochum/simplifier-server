package com.oljochum.simplifier_server.simplify;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class SimplifyServiceImpl implements SimplifyService {
    private final WebClient webClient = WebClient.create("http://localhost:8000");

    public String llamaTest() {
        return webClient.get()
                .uri("/llama_test")
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"))
                .block();
    }

    public String callSimplipy(SimplifyRequestDTO req) {
        try {
            return webClient.post()
                .uri("/simplipy")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"))
                .block();
    } catch (WebClientResponseException e) {
        // Log status code, headers, and response body
        System.out.println("Status code: " + e.getStatusCode());
        System.out.println("Response body: " + e.getResponseBodyAsString());
        System.out.println("Headers: " + e.getHeaders());
        throw e; // optionally re-throw
    }
    }
}
