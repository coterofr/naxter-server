package com.platform.naxterbackend.subscription.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class Join {

    @NotBlank
    @Size(min = 5, max = 25)
    private String subscriber;

    @NotBlank
    @Size(min = 5, max = 25)
    private String producer;
}
