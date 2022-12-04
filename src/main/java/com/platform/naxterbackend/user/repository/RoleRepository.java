package com.platform.naxterbackend.user.repository;

import com.platform.naxterbackend.user.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByType(String type);
}
