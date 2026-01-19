package com.oljochum.simplifier_server.simplify;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SynonymRequestDTO(@JsonProperty("input_word") String input_word, @JsonProperty("sentence") String sentence) {}
