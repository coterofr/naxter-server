package com.platform.naxterbackend.post.model;

import com.platform.naxterbackend.comment.model.Comment;
import com.platform.naxterbackend.theme.model.Theme;
import com.platform.naxterbackend.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document("post")
public class Post {

    @Id
    private String id;

    @NotBlank
    @Size(min = 5, max = 25)
    private String name;

    @Size(min = 5, max = 500)
    private String description;

    @Min(0)
    private BigInteger numRatings;

    @Min(0)
    @Max(10)
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal rating;

    private Boolean subscriber;

    private String multimedia;

    @NotNull
    @DBRef
    private Theme theme;

    @NotNull
    @DBRef
    private User user;

    @NotNull
    private Date date;

    @DBRef
    private List<Tag> tags;

    @ReadOnlyProperty
    @DocumentReference(lookup="{'post':?#{#self._id}}")
    private List<Comment> comments;


    public Post() { }

    public Post(String name,
                String description,
                BigInteger numRatings,
                BigDecimal rating,
                Boolean subscriber,
                String multimedia,
                User user,
                Theme theme,
                Date date,
                List<Tag> tags) {
        this.name = name;
        this.description = description;
        this.numRatings = numRatings;
        this.rating = rating;
        this.subscriber = subscriber;
        this.multimedia = multimedia;
        this.user = user;
        this.theme = theme;
        this.date = date;
        this.tags = tags;
        this.comments = new ArrayList<>();
    }
}
