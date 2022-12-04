package com.platform.naxterbackend.post.service;

import com.platform.naxterbackend.post.model.Post;
import com.platform.naxterbackend.post.model.PostTag;
import com.platform.naxterbackend.post.model.Tag;
import com.platform.naxterbackend.post.repository.PostRepository;
import com.platform.naxterbackend.post.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PostRepository postRepository;


    @Override
    public Tag addTag(String id, PostTag postTag) {
        Post post = this.postRepository.findById(id).get();
        Tag tag = new Tag(postTag.getName(), postTag.getDescription());

        Tag tagAdded = this.tagRepository.save(tag);

        List<Tag> tags = post.getTags();
        tags.add(tagAdded);

        this.postRepository.save(post);

        return tagAdded;
    }

    @Override
    public List<Tag> getTags() {
        return this.tagRepository.findAll();
    }

    @Override
    public Page<Tag> getTagsByPost(String id, Pageable pageable) {
        Post post = this.postRepository.findById(id).get();
        List<Tag> tags = post.getTags();

        return new PageImpl<>(tags, pageable, tags.size());
    }

    @Override
    public String delete(String id) {
        Tag tag = this.tagRepository.findById(id).get();

        this.tagRepository.delete(tag);

        return id;
    }
}
