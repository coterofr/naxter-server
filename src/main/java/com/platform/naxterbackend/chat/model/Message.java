package com.platform.naxterbackend.chat.model;

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
@Document("message")
public class Message {

    @Id
    private String id;

    @NotNull
    @DBRef
    private User emitter;

    @NotNull
    @DBRef
    private User receiver;

    @NotBlank
    @Size(min = 1, max = 250)
    private String message;

    @NotNull
    private Date date;


    public Message() { }

    public Message(User emitter, User receiver, String message, Date date) {
        this.emitter = emitter;
        this.receiver = receiver;
        this.message = message;
        this.date = date;
    }
}
