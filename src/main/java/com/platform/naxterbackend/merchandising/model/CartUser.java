package com.platform.naxterbackend.merchandising.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CartUser {

    @NotBlank
    @Size(min = 5, max = 25)
    private String buyer;

    @NotBlank
    private String product;
}
