package com.platform.naxterbackend.comment.controller;

import com.platform.naxterbackend.comment.model.Comment;
import com.platform.naxterbackend.comment.model.PostComment;
import com.platform.naxterbackend.comment.service.CommentService;
import com.platform.naxterbackend.comment.validator.CommentValidator;
import com.platform.naxterbackend.post.validator.PostValidator;
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

@RestController
@RequestMapping("/posts/{idPost}/comments/")
public class CommentController {

    private final static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;


    @PreAuthorize("hasRole('GENERIC')")
    @GetMapping(
        value = {"", "/list"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> getComments(Model model,
                                         @PathVariable(name = "idPost") String id) {
        if(!PostValidator.validId(id)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            return ResponseEntity.ok().body(this.commentService.getComments(id));
        }
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping(
        value = { "/{id}"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> get(Model model,
                                 @PathVariable(name = "idPost") String idPost,
                                 @PathVariable(name = "id") String id) {
        if(!PostValidator.validId(idPost) && !CommentValidator.validId(id)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Comment comment = this.commentService.getComment(id);

            return ResponseEntity.ok().body(comment);
        }
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping(
        value = {"/add"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> add(Model model,
                                 @Valid @RequestBody PostComment postComment,
                                 BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Comment commentAdded = this.commentService.addComment(postComment);

            return ResponseEntity.ok().body(commentAdded);
        }
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping(
        value = {"/{id}/edit"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> edit(Model model,
                                  @PathVariable(name = "id") String id,
                                  @Valid @RequestBody PostComment postComment,
                                  BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Comment commentEdited = this.commentService.editComment(id, postComment);

            return ResponseEntity.ok().body(commentEdited);
        }
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping(
        value = {"/{id}/delete"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<String> delete(Model model,
                                         @RequestBody String id) {
        if(!CommentValidator.validId(id)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            String idDeleted = this.commentService.delete(id);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", idDeleted);

            return ResponseEntity.ok().body(jsonObject.toString());
        }
    }
}
