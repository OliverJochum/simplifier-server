package com.oljochum.simplifier_server.simplify;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SimplifyServiceImpl implements SimplifyService {
    private final WebClient webClient = WebClient.create("http://localhost:8000");

    public String callSimplipy() {
        return webClient.get()
                .uri("/llama_test")
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"))
                .block();
    }
}
