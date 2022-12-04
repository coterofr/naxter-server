package com.platform.naxterbackend.subscription.repository;

import com.platform.naxterbackend.subscription.model.Subscription;
import com.platform.naxterbackend.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {

    List<Subscription> findAllBySubscriber(User subscriber);

    List<Subscription> findAllByProducer(User producer);

    Subscription findBySubscriberAndProducer(User subscriber, User producer);

    void deleteAllBySubscriber(User user);

    void deleteAllByProducer(User user);
}
