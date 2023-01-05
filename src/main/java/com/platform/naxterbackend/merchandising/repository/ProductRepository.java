package com.platform.naxterbackend.merchandising.repository;

import com.platform.naxterbackend.merchandising.model.Product;
import com.platform.naxterbackend.user.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{ 'name' : { '$regex' : ?0 , $options : 'i'} }")
    List<Product> findAllByName(String name, Sort sort);

    @Query("{ 'name' : { '$regex' : ?0 , $options : 'i'}, 'merchandising.$id' : { $in : ?1 } }")
    List<Product> findAllByNameAndMerchandising(String name, List<String> merchandising, Sort sort);

    @Query("{ 'name' : { '$regex' : ?0 , $options : 'i'}, 'user.$id' : { '$regex' : ?1 , $options : 'i'} }")
    List<Product> findAllByNameAndUser(String name, String user, Sort sort);

    @Query("{ 'merchandising.$id' : { $in : ?0 }}")
    List<Product> findAllByMerchandising(List<String> merchandising, Sort sort);

    @Query("{ 'merchandising.$id' : { $in : ?0 }, 'user.$id' : { '$regex' : ?1 , $options : 'i'} }")
    List<Product> findAllByMerchandisingAndUser(List<String> merchandising, String user, Sort sort);

    @Query("{ 'name' : { '$regex' : ?0 , $options : 'i'}, 'user.$id' : { '$regex' : ?1 , $options : 'i'} }")
    List<Product> findAllByNameAndMerchandisingAndUser(String name, String user, Sort sort);

    List<Product> findAllByUser(User user);

    void deleteAllByUser(User user);
}
