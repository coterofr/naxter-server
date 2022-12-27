package com.platform.naxterbackend.merchandising.service;

import com.platform.naxterbackend.merchandising.model.Product;
import com.platform.naxterbackend.merchandising.model.ProductMerch;

import java.util.List;

public interface ProductService {

    Product getProduct(String id);

    List<Product> getProducts();

    List<Product> searchProducts(String name, List<String> merchandising, String user);

    Product createProduct(ProductMerch productMerch);

    Product editProduct(String id, ProductMerch productMerch);

    String delete(String id);
}
