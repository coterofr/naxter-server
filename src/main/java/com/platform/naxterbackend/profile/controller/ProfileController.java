package com.platform.naxterbackend.profile.controller;

import com.platform.naxterbackend.profile.model.Account;
import com.platform.naxterbackend.profile.model.Visualization;
import com.platform.naxterbackend.profile.service.ProfileService;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.service.UserService;
import com.platform.naxterbackend.user.validator.UserValidator;
import org.json.JSONObject;
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
@RequestMapping("/profiles")
public class ProfileController {

    private final static Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('GENERIC')")
    @GetMapping(
        value = {"", "/list"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<User>> getProfiles(Model model) {
        return ResponseEntity.ok().body(this.userService.getUsers());
    }

    @PreAuthorize("hasRole('GENERIC')")
    @GetMapping(
        value = {"/search"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<User>> searchProfiles(Model model,
                                                     @RequestParam("name") String name) {
        return ResponseEntity.ok().body(this.userService.searchUsers(name));
    }

    @PreAuthorize("hasRole('GENERIC')")
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

    @PreAuthorize("hasRole('GENERIC')")
    @GetMapping(
        value = { "/{id}/account"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> getAccount(Model model,
                                        @PathVariable(name = "id") String name) {
        if(!UserValidator.validName(name)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            User user = this.userService.getUser(name);

            return ResponseEntity.ok().body(user);
        }
    }

    @PreAuthorize("hasRole('GENERIC')")
    @PostMapping(
        value = {"{id}/account/edit"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> editAccount(Model model,
                                         @PathVariable(name = "id") String name,
                                         @Valid @RequestBody Account account,
                                         BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else if(Boolean.TRUE.equals(userService.exitsOtherUserByName(name, account.getName()))) {
            return ResponseEntity.badRequest().body("Profile user already exists by name");
        } else if(Boolean.TRUE.equals(userService.exitsOtherUserByEmail(name, account.getEmail()))) {
            return ResponseEntity.badRequest().body("Profile user already exists by email");
        } else {
            User userEdited = this.profileService.editAccount(name, account);

            return ResponseEntity.ok().body(userEdited);
        }
    }

    @PreAuthorize("hasRole('GENERIC')")
    @PostMapping(
        value = {"/{id}/visit"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> visit(Model model,
                                   @PathVariable String id,
                                   @Valid @RequestBody Visualization visualization,
                                   BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else if(Boolean.TRUE.equals(visualization.getViewer().equals(visualization.getVisited()))) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Viewer user same as visited");

            return ResponseEntity.ok().body(jsonObject.toString());
        } else {
            User userEdited = this.profileService.visit(visualization);

            return ResponseEntity.ok().body(userEdited);
        }
    }
}
