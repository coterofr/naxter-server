package com.platform.naxterbackend.profile.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Visualization {

    @NotBlank
    private String viewer;

    @NotBlank
    private String visited;
}
