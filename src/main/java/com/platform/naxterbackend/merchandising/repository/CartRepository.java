package com.platform.naxterbackend.merchandising.repository;

import com.platform.naxterbackend.merchandising.model.Cart;
import com.platform.naxterbackend.merchandising.model.Product;
import com.platform.naxterbackend.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    Cart findByBuyer(User buyer);

    List<Cart> findByProductsContaining(Product product);
}
