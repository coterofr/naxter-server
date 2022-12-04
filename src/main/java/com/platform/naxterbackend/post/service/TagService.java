package com.platform.naxterbackend.post.service;

import com.platform.naxterbackend.post.model.PostTag;
import com.platform.naxterbackend.post.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    Tag addTag(String id, PostTag postTag);

    List<Tag> getTags();

    Page<Tag> getTagsByPost(String id, Pageable pageable);

    String delete(String id);
}
