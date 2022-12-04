package com.platform.naxterbackend.auth.controller;

import com.platform.naxterbackend.auth.jwt.JwtProvider;
import com.platform.naxterbackend.auth.model.JwtToken;
import com.platform.naxterbackend.auth.model.LoginUser;
import com.platform.naxterbackend.auth.model.RegisterUser;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.service.UserService;
import com.platform.naxterbackend.user.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping(
        value = {"/register"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> register(Model model,
                                         @Valid @RequestBody RegisterUser registerUser,
                                         BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else if(Boolean.TRUE.equals(this.userService.exitsUserByName(registerUser.getName()))) {
            return ResponseEntity.badRequest().body("User already exists by name");
        } else if(Boolean.TRUE.equals(this.userService.exitsUserByEmail(registerUser.getEmail()))) {
            return ResponseEntity.badRequest().body("User already exists by email");
        } else {
            String passwordEncoded = this.passwordEncoder.encode(registerUser.getPassword());
            User user = this.userService.register(registerUser, passwordEncoded);

            URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/{id}").buildAndExpand(user.getName()).toUri();
            return ResponseEntity.created(location).body(user.setPassword("**********"));
        }
    }

    private ResponseEntity<?> checkBlock(LoginUser loginUser) {
        if(Boolean.FALSE.equals(this.userService.isUserBlock(loginUser.getName()))) {
            Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getName(), loginUser.getPassword()));
            JwtToken jwtToken = this.userService.login(loginUser, authentication);

            return ResponseEntity.ok().body(jwtToken);
        } else {
            return ResponseEntity.badRequest().body("User is block");
        }
    }

    private ResponseEntity<?> validatePassword(LoginUser loginUser) {
        String passwordEncoded = this.userService.getUserPassword(loginUser.getName());
        if(Boolean.TRUE.equals(this.passwordEncoder.matches(loginUser.getPassword(), passwordEncoded))) {
            return this.checkBlock(loginUser);
        } else {
            return ResponseEntity.badRequest().body("User with this name not exists by password");
        }
    }

    @PostMapping(
        value = {"/login"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> login(Model model,
                                   @Valid @RequestBody LoginUser loginUser,
                                   BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else if(Boolean.FALSE.equals(this.userService.exitsUserByName(loginUser.getName()))) {
            return ResponseEntity.badRequest().body("User not exists by name");
        } else {
            return this.validatePassword(loginUser);
        }
    }

    @PostMapping(
        value = {"/refresh"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> refresh(Model model,
                                     @Valid @RequestBody JwtToken jwtToken,
                                     BindingResult result) throws ParseException {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            String tokenRefresh = this.jwtProvider.refreshJwtToken(jwtToken);
            JwtToken jwtTokenNew = new JwtToken(tokenRefresh);

            return ResponseEntity.ok().body(jwtTokenNew);
        }
    }

    @PostMapping(
        value = {"/refresh/{id}"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> refresh(Model model,
                                     @PathVariable String id,
                                     @Valid @RequestBody String name) {
        if(!UserValidator.validName(name)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            User user = this.userService.getUser(name);
            String tokenRefresh = this.jwtProvider.refreshJwtToken(user);
            JwtToken jwtTokenNew = new JwtToken(tokenRefresh);

            return ResponseEntity.ok().body(jwtTokenNew);
        }
    }
}
