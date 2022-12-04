package com.platform.naxterbackend.post.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Component
public class PostTag {

    private String id;

    @NotBlank
    @Size(min = 3, max = 25)
    private String name;

    @Size(min = 0, max = 100)
    private String description;
}
