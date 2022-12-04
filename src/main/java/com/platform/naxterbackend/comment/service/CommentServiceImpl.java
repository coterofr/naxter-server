package com.platform.naxterbackend.comment.service;

import com.platform.naxterbackend.comment.model.Comment;
import com.platform.naxterbackend.comment.model.PostComment;
import com.platform.naxterbackend.comment.repository.CommentRepository;
import com.platform.naxterbackend.post.model.Post;
import com.platform.naxterbackend.post.repository.PostRepository;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;


    @Override
    public Comment getComment(String id) {
        return this.commentRepository.findById(id).get();
    }

    @Override
    public List<Comment> getComments(String id) {
        return this.commentRepository.findAllByPostIdOrderByDateDesc(id);
    }

    @Override
    @Transactional(readOnly = false)
    public Comment addComment(PostComment postComment) {
        Comment comment = new Comment();
        comment.setContent(postComment.getContent());

        User user = this.userRepository.findByNameIgnoreCase(postComment.getUser());
        comment.setUser(user);

        Post post = this.postRepository.findById(postComment.getPost()).get();
        comment.setPost(post);

        comment.setDate(new Date());

        return this.commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = false)
    public Comment editComment(String id, PostComment postComment) {
        Comment comment = this.commentRepository.findById(id).get();
        comment.setContent(postComment.getContent());

        return this.commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = false)
    public String delete(String id) {
        Comment comment = this.commentRepository.findById(id).get();

        this.commentRepository.delete(comment);

        return id;
    }
}
