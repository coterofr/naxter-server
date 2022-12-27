package com.platform.naxterbackend.merchandising.service;

import com.platform.naxterbackend.merchandising.model.Cart;
import com.platform.naxterbackend.merchandising.model.CartUser;

public interface CartService {

    Cart getCart(String name);

    Cart addProductCart(CartUser cartUser);

    String removeProductCart(CartUser cartUser);
}
