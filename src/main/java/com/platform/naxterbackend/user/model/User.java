package com.platform.naxterbackend.user.model;

import com.platform.naxterbackend.merchandising.model.Merchandising;
import com.platform.naxterbackend.profile.model.Profile;
import com.platform.naxterbackend.theme.model.Theme;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document("user")
public class User {

    @Id
    @Size(min = 5, max = 25)
    private String name;

    @NotBlank
    @Size(min = 10, max = 50)
    private String email;

    @NotBlank
    @Size(min = 5, max = 50)
    private String userName;

    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    @NotNull
    private Boolean block;

    @Min(0)
    @Max(10)
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal rating;

    @NotEmpty
    private List<Role> roles;

    @DBRef
    private Profile profile;

    @DBRef
    private List<Theme> themes;

    @DBRef
    private Merchandising merchandising;


    public User() { }

    public User(String name,
                String email,
                String userName,
                String password,
                Boolean block,
                BigDecimal rating,
                List<Role> roles,
                Profile profile,
                Merchandising merchandising) {
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.block = block;
        this.rating = rating;
        this.roles = roles;
        this.profile = profile;
        this.themes = new ArrayList<>();
        this.merchandising = merchandising;
    }

    public User setPassword(String password) {
        this.password = password;

        return this;
    }
}
