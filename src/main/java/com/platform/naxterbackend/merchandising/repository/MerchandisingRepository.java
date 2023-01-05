package com.platform.naxterbackend.merchandising.repository;

import com.platform.naxterbackend.merchandising.model.Merchandising;
import com.platform.naxterbackend.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchandisingRepository extends MongoRepository<Merchandising, String> {

    Merchandising findByNameIgnoreCase(String name);

    List<Merchandising> findAllByUser(User user);

    void deleteAllByUser(User user);
}
