package com.platform.naxterbackend.user.repository;

import com.platform.naxterbackend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByNameIgnoreCase(String name);

    Page<User> findAll(Pageable pageable);

    @Query("{ 'name' : { '$regex' : ?0 , $options: 'i'}}")
    List<User> findAllByName(String name, Sort sort);

    Boolean existsByNameIgnoreCase(String name);

    Boolean existsByEmailIgnoreCase(String email);

    User save(User user);
}
