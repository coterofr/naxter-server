package com.platform.naxterbackend.profile.service;

import com.platform.naxterbackend.profile.model.Account;
import com.platform.naxterbackend.profile.model.Profile;
import com.platform.naxterbackend.profile.model.Visualization;
import com.platform.naxterbackend.profile.repository.ProfileRepository;
import com.platform.naxterbackend.subscription.model.Subscription;
import com.platform.naxterbackend.subscription.repository.SubscriptionRepository;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;


    public List<Profile> getProfiles() {
        return this.profileRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public User editAccount(String name, Account account) {
        User user = this.userRepository.findByNameIgnoreCase(name);
        List<Subscription> subscriptionsSubscriber = this.subscriptionRepository.findAllBySubscriber(user);
        List<Subscription> subscriptionsProducer = this.subscriptionRepository.findAllByProducer(user);

        if(Boolean.FALSE.equals(name.equals(account.getName()))) {
            this.userRepository.delete(user);
        }

        user.setName(account.getName());
        user.setEmail(account.getEmail());
        user.setUserName(account.getUserName());

        User userEdited = this.userRepository.save(user);

        for(Subscription subscription : subscriptionsSubscriber) {
            subscription.setSubscriber(userEdited);

            this.subscriptionRepository.save(subscription);
        }

        for(Subscription subscription : subscriptionsProducer) {
            subscription.setProducer(userEdited);

            this.subscriptionRepository.save(subscription);
        }

        Profile profile = userEdited.getProfile();
        profile.setDescription(account.getDescription());
        profile.setDateBirth(account.getDateBirth());

        this.profileRepository.save(profile);

        return userEdited;
    }

    @Override
    @Transactional(readOnly = false)
    public User visit(Visualization visualization) {
        User user = this.userRepository.findByNameIgnoreCase(visualization.getVisited());
        Profile profile = user.getProfile();
        profile.setVisits(profile.getVisits() + 1);

        this.profileRepository.save(profile);

        return user;
    }
}
