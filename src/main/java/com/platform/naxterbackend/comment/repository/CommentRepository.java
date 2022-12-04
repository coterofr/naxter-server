package com.platform.naxterbackend.comment.repository;

import com.platform.naxterbackend.comment.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByPostIdOrderByDateDesc(String id);

    Comment save(Comment comment);
}
