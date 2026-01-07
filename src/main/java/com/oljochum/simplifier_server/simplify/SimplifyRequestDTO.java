package com.oljochum.simplifier_server.simplify;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimplifyRequestDTO {

    private String input;

    @JsonCreator
    public SimplifyRequestDTO(@JsonProperty("input") String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }


}
