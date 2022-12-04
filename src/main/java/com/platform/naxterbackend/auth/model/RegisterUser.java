package com.platform.naxterbackend.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Component
public class RegisterUser {

    @NotBlank
    @Size(min = 5, max = 25)
    private String name;

    @NotBlank
    @Size(min = 10, max = 50)
    private String email;

    @NotBlank
    @Size(min = 5, max = 50)
    private String userName;

    @NotBlank
    @Size(min = 5, max = 50)
    private String password;
}
