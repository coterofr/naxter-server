package com.platform.naxterbackend.chat.controller;

import com.platform.naxterbackend.chat.model.Chat;
import com.platform.naxterbackend.chat.model.ChatMessage;
import com.platform.naxterbackend.chat.model.Message;
import com.platform.naxterbackend.chat.service.ChatService;
import com.platform.naxterbackend.user.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/chat/{idUser1}/{idUser2}/")
public class ChatController {

    private final static Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;


    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping(
        value = {"/chat"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> getChat(Model model,
                                     @PathVariable(name = "idUser1") String idUser1,
                                     @PathVariable(name = "idUser2") String idUser2) {
        if(!UserValidator.validName(idUser1) || !UserValidator.validName(idUser2)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            return ResponseEntity.ok().body(this.chatService.getChat(idUser1, idUser2));
        }
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping(
            value = {"/messages"},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> getMessages(Model model,
                                         @PathVariable(name = "idUser1") String idUser1,
                                         @PathVariable(name = "idUser2") String idUser2) {
        if(!UserValidator.validName(idUser1) || !UserValidator.validName(idUser2)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Chat chat = this.chatService.getChat(idUser1, idUser2);
            List<Message> messages = chat.getMessages();

            return ResponseEntity.ok().body(messages);
        }
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping(
        value = {"/send"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> send(Model model,
                                  @Valid @RequestBody ChatMessage chatMessage,
                                  BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Message messageSended = this.chatService.sendMessage(chatMessage);

            return ResponseEntity.ok().body(messageSended);
        }
    }
}
