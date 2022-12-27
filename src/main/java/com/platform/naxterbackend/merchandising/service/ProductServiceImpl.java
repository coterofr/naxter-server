package com.platform.naxterbackend.merchandising.service;

import com.platform.naxterbackend.merchandising.model.*;
import com.platform.naxterbackend.merchandising.repository.CartRepository;
import com.platform.naxterbackend.merchandising.repository.MerchandisingRepository;
import com.platform.naxterbackend.merchandising.repository.ProductRepository;
import com.platform.naxterbackend.post.validator.SearchValidator;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MerchandisingRepository merchandisingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;


    @Override
    public Product getProduct(String id) {
        return this.productRepository.findById(id).get();
    }

    @Override
    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public List<Product> searchProducts(String name, List<String> merchandising, String user) {
        List<Product> products = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        if(SearchValidator.validParam(name) && !SearchValidator.validParam(merchandising) && !SearchValidator.validParam(user)) {
            products = this.productRepository.findAllByName(name, sort);
        } else if(SearchValidator.validParam(name) && SearchValidator.validParam(merchandising) && !SearchValidator.validParam(user)) {
            products = this.productRepository.findAllByNameAndMerchandising(name, merchandising, sort);
        } else if(SearchValidator.validParam(name) && !SearchValidator.validParam(merchandising) && SearchValidator.validParam(user)) {
            products = this.productRepository.findAllByNameAndUser(name, user, sort);
        } else if(!SearchValidator.validParam(name) && SearchValidator.validParam(merchandising) && !SearchValidator.validParam(user)) {
            products = this.productRepository.findAllByMerchandising(merchandising, sort);
        } else if(!SearchValidator.validParam(name) && SearchValidator.validParam(merchandising) && SearchValidator.validParam(user)) {
            products = this.productRepository.findAllByMerchandisingAndUser(merchandising, user, sort);
        } else {
            products = this.productRepository.findAllByNameAndMerchandisingAndUser(name, user, sort);
        }
        products = products.stream().filter(p -> p.getUser().getMerchandising()).collect(Collectors.toList());

        return products;
    }

    @Override
    @Transactional(readOnly = false)
    public Product createProduct(ProductMerch productMerch) {
        Product product = new Product();
        product.setName(productMerch.getName());
        product.setDescription(productMerch.getDescription());
        product.setPrice(productMerch.getPrice());
        product.setStock(productMerch.getStock());

        User user = this.userRepository.findByNameIgnoreCase(productMerch.getUser());
        product.setUser(user);

        Merchandising merchandising = this.merchandisingRepository.findByNameIgnoreCase(productMerch.getName());
        if(merchandising == null) {
            merchandising = new Merchandising();
            merchandising.setName(productMerch.getMerchandising());
            merchandising.setUser(user);
            merchandising.setDate(new Date());

            this.merchandisingRepository.save(merchandising);
        }
        product.setMerchandising(merchandising);

        product.setDate(new Date());

        return this.productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = false)
    public Product editProduct(String id, ProductMerch productMerch) {
        Product product = this.productRepository.findById(id).get();
        product.setName(productMerch.getName());
        product.setDescription(productMerch.getDescription());
        product.setPrice(productMerch.getPrice());
        product.setStock(productMerch.getStock());

        return this.productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = false)
    public String delete(String id) {
        Product product = this.productRepository.findById(id).get();
        List<Cart> carts = this.cartRepository.findByProductsContaining(product);
        for(Cart cart : carts) {
            CartUser cartUser = new CartUser();
            cartUser.setBuyer(cart.getBuyer().getName());
            cartUser.setProduct(product.getId());
            this.cartService.removeProductCart(cartUser);
        }

        this.productRepository.delete(product);

        return id;
    }
}
