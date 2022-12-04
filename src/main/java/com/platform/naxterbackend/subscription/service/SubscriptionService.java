package com.platform.naxterbackend.subscription.service;

import com.platform.naxterbackend.subscription.model.Join;
import com.platform.naxterbackend.subscription.model.Subscription;

import java.util.List;

public interface SubscriptionService {

    Subscription getSubscription(String subscriber, String producer);

    List<Subscription> getSubscriptions();

    List<Subscription> getSubscriptionsBySubscriber(String name);

    Subscription subscribe(Join join);

    void desubscribe(Join join);
}
