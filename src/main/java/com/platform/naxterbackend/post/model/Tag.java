package com.platform.naxterbackend.post.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Document("tag")
public class Tag {

    @Id
    private String id;

    @NotBlank
    @Size(min = 3, max = 25)
    private String name;

    @Size(min = 0, max = 100)
    private String description;


    public Tag() { }

    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
