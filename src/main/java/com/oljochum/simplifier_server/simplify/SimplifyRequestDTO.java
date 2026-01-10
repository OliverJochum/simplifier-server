package com.oljochum.simplifier_server.simplify;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimplifyRequestDTO {

    private String input_text;
    private String selected_service;

    @JsonCreator
    public SimplifyRequestDTO(@JsonProperty("input_text") String input_text, @JsonProperty("selected_service") String selected_service) {
        this.input_text = input_text;
        this.selected_service = selected_service;
    }

    public String getInput_text() {
        return input_text;
    }

    public String getSelected_service() {
        return selected_service;
    }


}
