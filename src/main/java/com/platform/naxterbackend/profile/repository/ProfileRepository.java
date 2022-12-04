package com.platform.naxterbackend.profile.repository;

import com.platform.naxterbackend.profile.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> { }
