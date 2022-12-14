package com.platform.naxterbackend.comment.repository;

import com.platform.naxterbackend.comment.model.Comment;
import com.platform.naxterbackend.post.model.Post;
import com.platform.naxterbackend.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByPostIdOrderByDateDesc(String id);

    List<Comment> findAllByUser(User user);

    Comment save(Comment comment);

    void deleteAllByPost(Post post);
}
