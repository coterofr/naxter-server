package com.platform.naxterbackend.subscription.controller;

import com.platform.naxterbackend.subscription.model.Join;
import com.platform.naxterbackend.subscription.model.Subscription;
import com.platform.naxterbackend.subscription.service.SubscriptionService;
import com.platform.naxterbackend.user.validator.UserValidator;
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
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final static Logger logger = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private SubscriptionService subscriptionService;


    @PreAuthorize("hasRole('GENERIC')")
    @GetMapping(
        value = { "/subscriber/{id}"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<List<Subscription>> getSubscriptions(Model model,
                                                               @PathVariable(name = "id") String subscriber) {
        return ResponseEntity.ok().body(this.subscriptionService.getSubscriptionsBySubscriber(subscriber));
    }

    @PreAuthorize("hasRole('GENERIC')")
    @GetMapping(
        value = { "/subscriber/{idSubscriber}/producer/{idProducer}"},
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> getSubscription(Model model,
                                             @PathVariable String idSubscriber,
                                             @PathVariable String idProducer) {
        if(!UserValidator.validName(idSubscriber) && !UserValidator.validName(idProducer)) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            return ResponseEntity.ok().body(this.subscriptionService.getSubscription(idSubscriber, idProducer));
        }
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping(
        value = {"/{id}/subscribe"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> subscribe(Model model,
                                       @PathVariable String id,
                                       @Valid @RequestBody Join join,
                                       BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            Subscription subscription = this.subscriptionService.subscribe(join);

            return ResponseEntity.ok().body(subscription);
        }
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping(
        value = {"/{id}/unsubscribe"},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
    )
    public ResponseEntity<?> desubscribe(Model model,
                                         @PathVariable String id,
                                         @Valid @RequestBody Join join,
                                         BindingResult result) {
        if(Boolean.TRUE.equals(result.hasErrors())) {
            return ResponseEntity.badRequest().body("Request with errors");
        } else {
            this.subscriptionService.desubscribe(join);

            return ResponseEntity.ok().build();
        }
    }
}
