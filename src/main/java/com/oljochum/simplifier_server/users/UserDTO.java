package com.oljochum.simplifier_server.users;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDTO(@JsonProperty("username") String username, @JsonProperty("password") String password) {
    
}
