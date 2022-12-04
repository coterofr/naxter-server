package com.platform.naxterbackend.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Document("role")
public class Role {

    @Id
    private String type;

    @NotBlank
    @Size(min = 5, max = 25)
    private String description;


    public Role() { }

    public Role(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
