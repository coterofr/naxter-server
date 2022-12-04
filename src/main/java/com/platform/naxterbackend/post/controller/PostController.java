package com.platform.naxterbackend.post.controller;

import com.platform.naxterbackend.post.model.Post;
import com.platform.naxterbackend.post.model.PostTag;
import com.platform.naxterbackend.post.model.Tag;
import com.platform.naxterbackend.post.model.UserPost;
import com.platform.naxterbackend.post.service.PostService;
import com.platform.naxterbackend.post.service.TagService;
import com.platform.naxterbackend.post.validator.PostValidator;
import com.platform.naxterbackend.user.model.User;
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
@RequestMapping("/posts")
public class PostController {

    private final static Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;


    @PreAuthorize("hasRole('GENERIC')")
    @GetMapping(
        value = {"", "/list"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<Post>> getPosts(Model model) {
        return ResponseEntity.ok().body(this.postService.getPosts());
    }

    @GetMapping(
            value = { "/home/list"},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<Post>> getTopPosts(Model model) {
        return ResponseEntity.ok().body(this.postService.getTopPosts());
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping(
            value = {"/users/{id}/top/list"},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<Post>> getTopPostsByAuthor(Model model,
                                                          @PathVariable(name = "id") String name) {
        return ResponseEntity.ok().body(this.postService.getTopPostsByAuthor(name));
    }

    @PreAuthorize("hasRole('GENERIC')")
    @GetMapping(
        value = {"/search"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<Post>> searchPosts(Model model,
                                                  @RequestParam("theme") String theme,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("user") String user) {
        return ResponseEntity.ok().body(this.postService.searchPosts(name, theme, user));
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @GetMapping(
        value = { "/{id}"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> get(Model model,
                                 @PathVariable(name = "id") String id) {
        if(!PostValidator.validId(id)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Post post = this.postService.getPost(id);

            return ResponseEntity.ok().body(post);
        }
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping(
        value = {"/create"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> create(Model model,
                                    @Valid @RequestBody UserPost userPost,
                                    BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Post postCreated = this.postService.createPost(userPost);

            return ResponseEntity.ok().body(postCreated);
        }
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping(
        value = {"/{id}/edit"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> edit(Model model,
                                  @PathVariable(name = "id") String id,
                                  @Valid @RequestBody UserPost userPost,
                                  BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Post postEdited = this.postService.editPost(id, userPost);

            return ResponseEntity.ok().body(postEdited);
        }
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping(
        value = {"/{id}/delete"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<String> delete(Model model,
                                         @RequestBody String id) {
        if(!PostValidator.validId(id)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            String idDeleted = this.postService.delete(id);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", idDeleted);

            return ResponseEntity.ok().body(jsonObject.toString());
        }
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @GetMapping(
        value = {"/{id}/tags", "/{id}/tags/list"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<Page<Tag>> getTags(Model model,
                                             @PageableDefault(
                                                size = 100,
                                                page = 0,
                                                direction = Sort.Direction.ASC,
                                                sort = "name"
                                             ) Pageable pageable,
                                             @RequestParam("id") String id) {
        return ResponseEntity.ok().body(this.tagService.getTagsByPost(id, pageable));
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping(
        value = {"/tags/add", "/{id}/tags/add"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> addTag(Model model,
                                    @PathVariable(name = "id") String id,
                                    @Valid @RequestBody PostTag postTag,
                                    BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Tag tagAdded = this.tagService.addTag(id, postTag);

            return ResponseEntity.ok().body(tagAdded);
        }
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping(
        value = {"/tags/{idTag}/delete", "/{idPost}/tags/{idTag}/delete"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<String> deleteTag(Model model,
                                            @RequestBody String id) {
        if(!PostValidator.validId(id)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            String idDeleted = this.tagService.delete(id);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", idDeleted);

            return ResponseEntity.ok().body(jsonObject.toString());
        }
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping(
        value = {"/{id}/rate"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> rate(Model model,
                                  @PathVariable String id,
                                  @RequestBody Integer rating) {
        if(!PostValidator.validId(id)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Post postRated = this.postService.rate(id, rating);

            return ResponseEntity.ok().body(postRated);
        }
    }
}
