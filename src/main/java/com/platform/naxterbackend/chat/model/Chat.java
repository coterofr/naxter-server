package com.platform.naxterbackend.chat.model;

import com.platform.naxterbackend.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Document("chat")
public class Chat {

    @Id
    private String id;

    @NotNull
    @DBRef
    private User user1;

    @NotNull
    @DBRef
    private User user2;

    @NotEmpty
    @DBRef
    private List<Message> messages;


    public Chat() { }

    public Chat(User user1, User user2, List<Message> messages) {
        this.user1 = user1;
        this.user2 = user2;
        this.messages = messages;
    }
}
