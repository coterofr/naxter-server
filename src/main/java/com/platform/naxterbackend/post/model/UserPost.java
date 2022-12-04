package com.platform.naxterbackend.post.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserPost {

    private String id;

    @NotBlank
    @Size(min = 5, max = 25)
    private String name;

    @Size(min = 5, max = 500)
    private String description;

    @NotNull
    @Size(min = 5, max = 25)
    private String user;

    @NotNull
    @Size(min = 5, max = 25)
    private String theme;
}
