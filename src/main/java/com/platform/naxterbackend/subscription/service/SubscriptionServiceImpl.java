package com.platform.naxterbackend.subscription.service;

import com.platform.naxterbackend.subscription.model.Join;
import com.platform.naxterbackend.subscription.model.Subscription;
import com.platform.naxterbackend.subscription.repository.SubscriptionRepository;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository;


    public Subscription getSubscription(String subscriber, String producer) {
        User userSubscriber = this.userRepository.findByNameIgnoreCase(subscriber);
        User userProducer = this.userRepository.findByNameIgnoreCase(producer);

        return this.subscriptionRepository.findBySubscriberAndProducer(userSubscriber, userProducer);
    }

    public List<Subscription> getSubscriptions() {
        return this.subscriptionRepository.findAll();
    }

    public List<Subscription> getSubscriptionsBySubscriber(String name) {
        User subscriber = this.userRepository.findByNameIgnoreCase(name);

        return this.subscriptionRepository.findAllBySubscriber(subscriber);
    }

    @Override
    @Transactional(readOnly = false)
    public Subscription subscribe(Join join) {
        Subscription subscription = new Subscription();
        User subscriber = this.userRepository.findByNameIgnoreCase(join.getSubscriber());
        User producer = this.userRepository.findByNameIgnoreCase(join.getProducer());
        subscription.setSubscriber(subscriber);
        subscription.setProducer(producer);
        subscription.setTime(new Date());
        subscription.setVip(Boolean.FALSE);

        return this.subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional(readOnly = false)
    public void desubscribe(Join join) {
        User subscriber = this.userRepository.findByNameIgnoreCase(join.getSubscriber());
        User producer = this.userRepository.findByNameIgnoreCase(join.getProducer());

        Subscription subscription = this.subscriptionRepository.findBySubscriberAndProducer(subscriber, producer);

        this.subscriptionRepository.delete(subscription);
    }
}
