package com.platform.naxterbackend.user.controller;

import com.platform.naxterbackend.user.model.ConfigUser;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.service.UserService;
import com.platform.naxterbackend.user.validator.UserValidator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping(
        value = {"", "/list"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<Page<User>> getUsers(Model model,
                                               @PageableDefault(
                                                   size = 100,
                                                   page = 0,
                                                   direction = Sort.Direction.ASC,
                                                   sort = "name"
                                               ) Pageable pageable) {
        return ResponseEntity.ok().body(this.userService.getUsers(pageable));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping(
            value = {"/{id}/subscriptions/authors"},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<User>> getSubscribedAuthors(Model model,
                                                           @PathVariable(name = "id") String name) {
        return ResponseEntity.ok().body(this.userService.getSubscribedAuthors(name));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(
        value = { "/{id}"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> get(Model model,
                                 @PathVariable(name = "id") String name) {
        if(!UserValidator.validName(name)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            User user = this.userService.getUser(name);

            return ResponseEntity.ok().body(user);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(
        value = {"{id}/edit"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> edit(Model model,
                                  @PathVariable(name = "id") String name,
                                  @Valid @RequestBody ConfigUser configUser,
                                  BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else if(Boolean.TRUE.equals(this.userService.exitsOtherUserByName(name, configUser.getName()))) {
            return ResponseEntity.badRequest().body("User already exists by name");
        } else if(Boolean.TRUE.equals(this.userService.exitsOtherUserByEmail(name, configUser.getEmail()))) {
            return ResponseEntity.badRequest().body("User already exists by email");
        } else {
            User userEdited = this.userService.edit(name, configUser);

            return ResponseEntity.ok().body(userEdited);
        }
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping(
        value = {"/{id}/block"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> block(Model model,
                                   @PathVariable String id,
                                   @RequestBody String name) {
        if(!UserValidator.validName(name)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            User userBlocked = this.userService.block(name);

            return ResponseEntity.ok().body(userBlocked);
        }
    }

    @PreAuthorize("hasRole('GENERIC')")
    @PostMapping(
        value = {"/{id}/delete"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<String> delete(Model model,
                                         @PathVariable String id,
                                         @RequestBody String name) {
        if(!UserValidator.validName(name)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            String nameDeleted = this.userService.delete(name);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", nameDeleted);

            return ResponseEntity.ok().body(jsonObject.toString());
        }
    }

    @PreAuthorize("hasRole('GENERIC')")
    @PostMapping(
        value = {"/{id}/change-generic-role"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> changeGenericRole(Model model,
                                               @PathVariable String id,
                                               @RequestBody String name) {
        if(!UserValidator.validName(name)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            User user = this.userService.changeGenericRole(name);

            return ResponseEntity.ok().body(user);
        }
    }
}
