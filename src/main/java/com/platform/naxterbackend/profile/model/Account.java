package com.platform.naxterbackend.profile.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Component
public class Account {

    @NotBlank
    @Size(min = 5, max = 25)
    private String name;

    @NotBlank
    @Size(min = 10, max = 50)
    private String email;

    @NotBlank
    @Size(min = 5, max = 50)
    private String userName;

    @Size(min = 0, max = 500)
    private String description;

    private Date dateBirth;
}
