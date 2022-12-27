package com.platform.naxterbackend.merchandising.model;

import com.platform.naxterbackend.user.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Document("product")
public class Product {

    @Id
    private String id;

    @NotBlank
    @Size(min = 5, max = 25)
    private String name;

    @NotBlank
    @Size(min = 5, max = 500)
    private String description;

    @NotNull
    @Min(0)
    private BigDecimal price;

    @NotNull
    @Min(1)
    private Integer stock;

    private String multimedia;

    @NotNull
    @DBRef
    private Merchandising merchandising;

    @NotNull
    @DBRef
    private User user;

    @NotNull
    private Date date;


    public Product() { }

    public Product(String name,
                   String description,
                   BigDecimal price,
                   Integer stock,
                   String multimedia,
                   Merchandising merchandising,
                   User user,
                   Date date) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.multimedia = multimedia;
        this.merchandising = merchandising;
        this.user = user;
        this.date = date;
    }
}
