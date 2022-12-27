package com.platform.naxterbackend.merchandising.model;

import com.platform.naxterbackend.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Document("merchandising")
public class Merchandising {

    @Id
    @Size(min = 5, max = 25)
    private String name;

    @Size(min = 5, max = 500)
    private String description;

    @NotNull
    @DBRef
    private User user;

    @NotNull
    private Date date;


    public Merchandising() { }

    public Merchandising(String name,
                         String description,
                         User user,
                         Date date) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.date = date;
    }
}
