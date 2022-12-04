package com.platform.naxterbackend.post.repository;

import com.platform.naxterbackend.post.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> { }
