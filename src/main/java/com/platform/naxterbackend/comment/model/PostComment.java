package com.platform.naxterbackend.comment.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Component
public class PostComment {

    private String id;

    @NotBlank
    @Size(min = 1, max = 500)
    private String content;

    @NotBlank
    private String user;

    @NotBlank
    private String post;

    private Date date;
}
