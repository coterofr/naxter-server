package com.platform.naxterbackend.comment.model;

import com.platform.naxterbackend.post.model.Post;
import com.platform.naxterbackend.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Document("comment")
public class Comment {

    @Id
    private String id;

    @NotBlank
    @Size(min = 1, max = 500)
    private String content;

    @NotNull
    @DBRef
    private User user;

    @NotNull
    @DBRef
    private Post post;

    @NotNull
    private Date date;


    public Comment() { }

    public Comment(String content, User user, Post post, Date date) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.date = date;
    }
}
