package com.platform.naxterbackend.merchandising.controller;

import com.platform.naxterbackend.merchandising.model.*;
import com.platform.naxterbackend.merchandising.service.CartService;
import com.platform.naxterbackend.merchandising.service.MerchandisingService;
import com.platform.naxterbackend.merchandising.service.ProductService;
import com.platform.naxterbackend.post.validator.PostValidator;
import com.platform.naxterbackend.user.validator.UserValidator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/merchandising")
public class MerchandisingController {

    private final static Logger logger = LoggerFactory.getLogger(MerchandisingController.class);

    @Autowired
    private MerchandisingService merchandisingService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;


    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping(
            value = {"", "/list"},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<Merchandising>> getMerchandisings(Model model) {
        return ResponseEntity.ok().body(this.merchandisingService.getMerchandisings());
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping(
        value = {"/products", "/products/list"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<Product>> getMerchandising(Model model) {
        return ResponseEntity.ok().body(this.productService.getProducts());
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping(
            value = {"/products/search"},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<Product>> searchPosts(Model model,
                                                     @RequestParam("merchandising") List<String> merchandising,
                                                     @RequestParam("name") String name,
                                                     @RequestParam("user") String user) {
        return ResponseEntity.ok().body(this.productService.searchProducts(name, merchandising, user));
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping(
            value = { "/products/{id}"},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> get(Model model,
                                 @PathVariable(name = "id") String id) {
        if(!PostValidator.validId(id)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Product product = this.productService.getProduct(id);

            return ResponseEntity.ok().body(product);
        }
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping(
            value = {"/products/add"},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> add(Model model,
                                 @Valid @RequestBody ProductMerch productMerch,
                                 BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Product productCreated = this.productService.createProduct(productMerch);

            return ResponseEntity.ok().body(productCreated);
        }
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping(
            value = {"/products/{id}/edit"},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> edit(Model model,
                                  @PathVariable(name = "id") String id,
                                  @Valid @RequestBody ProductMerch productMerch,
                                  BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Product productEdited = this.productService.editProduct(id, productMerch);

            return ResponseEntity.ok().body(productEdited);
        }
    }

    @PreAuthorize("hasRole('PRODUCER')")
    @PostMapping(
            value = {"/products/{id}/delete"},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<String> delete(Model model,
                                         @RequestBody String id) {
        if(!PostValidator.validId(id)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            String idDeleted = this.productService.delete(id);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", idDeleted);

            return ResponseEntity.ok().body(jsonObject.toString());
        }
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping(
            value = { "/cart/{id}"},
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> getCart(Model model,
                                     @PathVariable(name = "id") String name) {
        if(!UserValidator.validName(name)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Cart cart = this.cartService.getCart(name);

            return ResponseEntity.ok().body(cart);
        }
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping(
            value = {"/cart/{id}/add"},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> addProductCart(Model model,
                                            @PathVariable(name = "id") String name,
                                            @Valid @RequestBody CartUser cartUser,
                                            BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Cart cart = this.cartService.addProductCart(cartUser);

            return ResponseEntity.ok().body(cart);
        }
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping(
            value = {"/cart/{id}/remove"},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> removeProductCart(Model model,
                                               @PathVariable(name = "id") String name,
                                               @Valid @RequestBody CartUser cartUser,
                                               BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            String idRemoved = this.cartService.removeProductCart(cartUser);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", idRemoved);

            return ResponseEntity.ok().body(jsonObject.toString());
        }
    }
}
