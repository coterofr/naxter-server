package com.platform.naxterbackend.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class JwtToken {

    private String token;


    public JwtToken() { }

    public JwtToken(String token) {
        this.token = token;
    }
}
