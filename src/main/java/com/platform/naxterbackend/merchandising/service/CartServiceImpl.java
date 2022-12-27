package com.platform.naxterbackend.merchandising.service;

import com.platform.naxterbackend.merchandising.model.Cart;
import com.platform.naxterbackend.merchandising.model.CartUser;
import com.platform.naxterbackend.merchandising.model.Product;
import com.platform.naxterbackend.merchandising.repository.CartRepository;
import com.platform.naxterbackend.merchandising.repository.ProductRepository;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Cart getCart(String name) {
        User buyer = this.userRepository.findByNameIgnoreCase(name);

        return this.cartRepository.findByBuyer(buyer);
    }

    @Override
    @Transactional(readOnly = false)
    public Cart addProductCart(CartUser cartUser) {
        User buyer = this.userRepository.findByNameIgnoreCase(cartUser.getBuyer());
        Cart cart = this.cartRepository.findByBuyer(buyer);
        if(cart == null) {
            cart = new Cart(buyer, new ArrayList<>());
        }

        Product product = this.productRepository.findById(cartUser.getProduct()).get();
        List<Product> products = cart.getProducts();
        products.add(product);

        return this.cartRepository.save(cart);
    }

    @Override
    @Transactional(readOnly = false)
    public String removeProductCart(CartUser cartUser) {
        User buyer = this.userRepository.findByNameIgnoreCase(cartUser.getBuyer());
        Cart cart = this.cartRepository.findByBuyer(buyer);

        Product product = this.productRepository.findById(cartUser.getProduct()).get();
        List<Product> products = cart.getProducts();
        products.removeIf(p -> product.getId().equals(p.getId()));

        this.cartRepository.save(cart);

        return cartUser.getProduct();
    }
}
