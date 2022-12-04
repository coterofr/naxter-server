package com.platform.naxterbackend.comment.service;

import com.platform.naxterbackend.comment.model.Comment;
import com.platform.naxterbackend.comment.model.PostComment;

import java.util.List;

public interface CommentService {

    Comment getComment(String id);

    List<Comment> getComments(String id);

    Comment addComment(PostComment postComment);

    Comment editComment(String id, PostComment postComment);

    String delete(String id);
}
