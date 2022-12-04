package com.platform.naxterbackend.subscription.model;

import com.platform.naxterbackend.theme.model.Theme;
import com.platform.naxterbackend.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Document("subscription")
public class Subscription {

    @Id
    private String id;

    @NotNull
    private Date time;

    @NotNull
    private Boolean vip;

    @NotNull
    @DBRef
    private User subscriber;

    @DBRef
    private User producer;

    @DBRef
    private Theme theme;


    public Subscription() { }

    public Subscription(Date time, Boolean vip, User subscriber, User producer, Theme theme) {
        this.time = time;
        this.vip = vip;
        this.subscriber = subscriber;
        this.producer = producer;
        this.theme = theme;
    }
}
