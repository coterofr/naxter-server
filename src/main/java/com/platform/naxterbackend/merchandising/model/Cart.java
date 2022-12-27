package com.platform.naxterbackend.merchandising.model;

import com.platform.naxterbackend.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Document("cart")
public class Cart {

    @Id
    private String id;

    @NotNull
    @DBRef
    private User buyer;

    @DBRef
    private List<Product> products;


    public Cart() { }

    public Cart(User buyer,
                List<Product> products) {
        this.buyer = buyer;
        this.products = products;
    }
}
