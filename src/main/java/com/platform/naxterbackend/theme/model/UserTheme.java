package com.platform.naxterbackend.theme.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Component
public class UserTheme {

    @NotNull
    @Size(min = 5, max = 25)
    private String name;

    @Size(min = 0, max = 500)
    private String description;

    @NotNull
    @Size(min = 5, max = 25)
    private String user;
}
