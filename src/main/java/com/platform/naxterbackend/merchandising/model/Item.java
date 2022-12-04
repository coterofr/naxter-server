package com.platform.naxterbackend.merchandising.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Document("item")
public class Item {

    @Id
    private String id;

    @NotBlank
    @Size(min = 5, max = 25)
    private String name;

    @NotBlank
    @Size(min = 5, max = 500)
    private String description;

    private String multimedia;


    public Item() { }

    public Item(String name, String description, String multimedia) {
        this.name = name;
        this.description = description;
        this.multimedia = multimedia;
    }
}
