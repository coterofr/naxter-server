package com.platform.naxterbackend.theme.repository;

import com.platform.naxterbackend.theme.model.Theme;
import com.platform.naxterbackend.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeRepository extends MongoRepository<Theme, String> {

    Theme findByNameIgnoreCase(String name);

    Boolean existsByNameIgnoreCase(String newName);

    List<Theme> findAllByOrderByNameAsc();

    List<Theme> findAllByUser(User user);

    @Query("{ 'name' : { '$regex' : ?0 , $options: 'i'}, 'user.$id' : ?1 }")
    List<Theme> findAllByNameAndUser(String name, String idUser);

    Theme save(Theme theme);
}
