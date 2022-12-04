package com.platform.naxterbackend.merchandising.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Document("merchandising")
public class Merchandising {

    @Id
    private String id;

    @NotBlank
    @Size(min = 5, max = 25)
    private String name;

    @Size(min = 5, max = 500)
    private String description;

    @DBRef
    private List<Item> items;


    public Merchandising() { }

    public Merchandising(String name, String description, List<Item> items) {
        this.name = name;
        this.description = description;
        this.items = items;
    }
}
