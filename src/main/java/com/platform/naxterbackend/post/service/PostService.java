package com.platform.naxterbackend.post.service;

import com.platform.naxterbackend.post.model.Post;
import com.platform.naxterbackend.post.model.UserPost;

import java.util.List;

public interface PostService {

    Post getPost(String id);

    List<Post> getPosts();

    List<Post> getTopPosts();

    List<Post> getTopPostsByAuthor(String name);

    List<Post> searchPosts(String name, String theme, String user);

    Post createPost(UserPost userPost);

    Post editPost(String id, UserPost userPost);

    String delete(String id);

    Post rate(String id, Integer newRating);
}
