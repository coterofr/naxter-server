package com.platform.naxterbackend.theme.model;

import com.platform.naxterbackend.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Document("theme")
public class Theme {

    @Id
    @Size(min = 5, max = 25)
    private String name;

    @Size(min = 0, max = 500)
    private String description;

    @NotNull
    @DBRef
    private User user;


    public Theme() { }

    public Theme(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }
}
