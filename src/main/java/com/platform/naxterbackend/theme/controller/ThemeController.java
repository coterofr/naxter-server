package com.platform.naxterbackend.theme.controller;

import com.platform.naxterbackend.theme.model.Theme;
import com.platform.naxterbackend.theme.model.UserTheme;
import com.platform.naxterbackend.theme.service.ThemeService;
import com.platform.naxterbackend.theme.validator.ThemeValidator;
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
@RequestMapping("/themes")
public class ThemeController {

    private final static Logger logger = LoggerFactory.getLogger(ThemeController.class);

    @Autowired
    private ThemeService themeService;


    @PreAuthorize("hasRole('PRODUCER')")
    @GetMapping(
        value = {"", "/list"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<Theme>> getThemes(Model model) {
        return ResponseEntity.ok().body(this.themeService.getThemes());
    }

    @PreAuthorize("hasRole('GENERIC')")
    @GetMapping(
        value = {"/users/{idUser}/search"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<Theme>> searchThemes(Model model,
                                                    @RequestParam("idUser") String idUser,
                                                    @RequestParam("name") String name) {
        return ResponseEntity.ok().body(this.themeService.searchThemes(idUser, name));
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @GetMapping(
        value = { "/{id}"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> get(Model model,
                                 @PathVariable(name = "id") String name) {
        if(!ThemeValidator.validName(name)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Theme theme = this.themeService.getTheme(name);

            return ResponseEntity.ok().body(theme);
        }
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping(
        value = {"/create"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> create(Model model,
                                    @Valid @RequestBody UserTheme userTheme,
                                    BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else if(Boolean.TRUE.equals(this.themeService.exitsThemeByName(userTheme.getName()))) {
            return ResponseEntity.badRequest().body("Theme already exists by name");
        } else {
            Theme themeCreated = this.themeService.createTheme(userTheme);

            return ResponseEntity.ok().body(themeCreated);
        }
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping(
        value = {"/{id}/edit"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> edit(Model model,
                                  @PathVariable(name = "id") String name,
                                  @Valid @RequestBody UserTheme userTheme,
                                  BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else if(Boolean.TRUE.equals(this.themeService.exitsOtherThemeByName(name, userTheme.getName()))) {
            return ResponseEntity.badRequest().body("Theme already exists by name");
        } else {
            Theme themeEdited = this.themeService.editTheme(name, userTheme);

            return ResponseEntity.ok().body(themeEdited);
        }
    }
}
