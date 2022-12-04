package com.platform.naxterbackend.profile.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Document("profile")
public class Profile {

    @Id
    private String id;

    @Size(min = 0, max = 500)
    private String description;

    private Date dateBirth;

    @Min(0)
    @Max(100)
    private Integer visits;


    public Profile() { }

    public Profile(String description, Date dateBirth, Integer visits) {
        this.description = description;
        this.dateBirth = dateBirth;
        this.visits = visits;
    }
}
