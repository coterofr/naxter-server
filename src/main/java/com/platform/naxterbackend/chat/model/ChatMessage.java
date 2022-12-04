package com.platform.naxterbackend.chat.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Component
public class ChatMessage {

    private String id;

    @NotBlank
    @Size(min = 5, max = 25)
    private String emitter;

    @NotBlank
    @Size(min = 5, max = 25)
    private String receiver;

    @NotBlank
    @Size(min = 1, max = 250)
    private String message;
}
