package com.oljochum.simplifier_server.simplify;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.oljochum.simplifier_server.analyse.AnalyzeServiceImpl;
import com.oljochum.simplifier_server.glossaries.GlossaryService;

@Service
public class SimplifyServiceImpl implements SimplifyService {
    private final WebClient webClient = WebClient.create("http://localhost:8000");

    private static final Logger logger = LoggerFactory.getLogger(SimplifyServiceImpl.class); 


    @Autowired GlossaryService glossaryService;

    public String llamaTest() {
        return webClient.get()
                .uri("/llama_test")
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"))
                .block();
    }

    public String callSimplipyGenerateText(SimplifyRequestDTO req) {
        try {
            return webClient.post()
                .uri("/generate_text")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"))
                .block();
    } catch (WebClientResponseException e) {
        System.out.println("Status code: " + e.getStatusCode());
        System.out.println("Response body: " + e.getResponseBodyAsString());
        System.out.println("Headers: " + e.getHeaders());
        throw e;
    }
    }

    @Override
    public String callSimplipySentenceSimplify(SimplifyRequestDTO req) {
        try {
            return webClient.post()
                .uri("/sentence_simplifications")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"))
                .block();
    } catch (WebClientResponseException e) {
        System.out.println("Status code: " + e.getStatusCode());
        System.out.println("Response body: " + e.getResponseBodyAsString());
        System.out.println("Headers: " + e.getHeaders());
        throw e;
    }
    }

    @Override
    public String callSimplipySentenceSuggest(SimplifyRequestDTO req) {
        try {
            return webClient.post()
                .uri("/sentence_suggestions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"))
                .block();
    } catch (WebClientResponseException e) {
        System.out.println("Status code: " + e.getStatusCode());
        System.out.println("Response body: " + e.getResponseBodyAsString());
        System.out.println("Headers: " + e.getHeaders());
        throw e;
    }
    }

    @Override
    public String callSimplipySynonyms(SynonymRequestDTO req) {
        try {
        return webClient.post()
                .uri("/synonyms")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .bodyToMono(String.class)  // just return JSON as a string
                .block();
        } catch (WebClientResponseException e) {
            System.out.println("Status code: " + e.getStatusCode());
            System.out.println("Response body: " + e.getResponseBodyAsString());
            System.out.println("Headers: " + e.getHeaders());
            throw e;
        }
    }

    @Override
    public String callSimplipyGenerateText(SimplifyRequestDTO req, Long glossaryId) {
        try {
            String glossaryString = glossaryService.stringifyGlossary(glossaryId);

            logger.info("Glossary string for glossaryId {}: {}", glossaryId, glossaryString);

            Object body = Map.of(
                "input_text", req.getInput_text(),
                "selected_service", req.getSelected_service(),
                "glossary_string", glossaryString
            );

            return webClient.post()
                .uri("/generate_text")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("response"))
                .block();
    } catch (WebClientResponseException e) {
        System.out.println("Status code: " + e.getStatusCode());
        System.out.println("Response body: " + e.getResponseBodyAsString());
        System.out.println("Headers: " + e.getHeaders());
        throw e;
    }
    }
}
