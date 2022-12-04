package com.platform.naxterbackend.user.service;

import com.platform.naxterbackend.auth.jwt.JwtProvider;
import com.platform.naxterbackend.auth.model.JwtToken;
import com.platform.naxterbackend.auth.model.LoginUser;
import com.platform.naxterbackend.auth.model.RegisterUser;
import com.platform.naxterbackend.profile.model.Profile;
import com.platform.naxterbackend.profile.repository.ProfileRepository;
import com.platform.naxterbackend.subscription.model.Subscription;
import com.platform.naxterbackend.subscription.repository.SubscriptionRepository;
import com.platform.naxterbackend.user.model.ConfigUser;
import com.platform.naxterbackend.user.model.Role;
import com.platform.naxterbackend.user.model.RoleEnum;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.repository.RoleRepository;
import com.platform.naxterbackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private JwtProvider jwtProvider;


    @Override
    public User getUser(String name) {
        return this.userRepository.findByNameIgnoreCase(name);
    }

    @Override
    public User getUserByName(String name) {
        return this.userRepository.findByNameIgnoreCase(name);
    }

    @Override
    public String getUserPassword(String name) {
        User user = this.userRepository.findByNameIgnoreCase(name);

        return user.getPassword();
    }

    @Override
    public Boolean exitsUserByName(String name) {
        return this.userRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public Boolean exitsOtherUserByName(String oldName, String newName) {
        return Boolean.TRUE.equals(!oldName.toLowerCase().equals(newName.toLowerCase()) && this.userRepository.existsByNameIgnoreCase(newName));
    }

    @Override
    public Boolean exitsUserByEmail(String email) {
        return this.userRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public Boolean exitsOtherUserByEmail(String name, String newEmail) {
        User user = this.userRepository.findByNameIgnoreCase(name);

        return Boolean.TRUE.equals(!user.getEmail().toLowerCase().equals(newEmail.toLowerCase()) && this.userRepository.existsByEmailIgnoreCase(newEmail));
    }

    @Override
    public Boolean isUserBlock(String name) {
        User user = this.userRepository.findByNameIgnoreCase(name);

        return user.getBlock();
    }

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public List<User> getSubscribedAuthors(String name) {
        User subscriber = this.userRepository.findByNameIgnoreCase(name);
        List<Subscription> subscriptions = this.subscriptionRepository.findAllBySubscriber(subscriber);

        List<User> producers = new ArrayList<>();
        for(Subscription subscription : subscriptions) {
            producers.add(subscription.getProducer());
        }

        return producers;
    }

    @Override
    public List<User> searchUsers(String name) {
        return this.userRepository.findAllByName(name, Sort.by(Sort.Direction.DESC, "rating"));
    }

    @Override
    @Transactional(readOnly = false)
    public User register(RegisterUser registerUser, String passwordEncoded) {
        List<Role> roles = new ArrayList<>();
        roles.add(this.roleRepository.findByType(RoleEnum.GENERIC.getType()));
        Profile profile = new Profile("", null, 0);

        Profile profileSaved = this.profileRepository.save(profile);

        User user = new User(registerUser.getName(), registerUser.getEmail(), registerUser.getUserName(), passwordEncoded, Boolean.FALSE, new BigDecimal(0.0), roles, profileSaved, null);

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = false)
    public JwtToken login(LoginUser loginUser, Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = this.jwtProvider.generateJwtToken(authentication);

        return new JwtToken(jwtToken);
    }

    @Override
    @Transactional(readOnly = false)
    public User edit(String name, ConfigUser configUser) {
        User user = this.userRepository.findByNameIgnoreCase(name);
        List<Subscription> subscriptionsSubscriber = this.subscriptionRepository.findAllBySubscriber(user);
        List<Subscription> subscriptionsProducer = this.subscriptionRepository.findAllByProducer(user);

        if(Boolean.FALSE.equals(name.equals(configUser.getName()))) {
            this.userRepository.delete(user);
        }

        user.setName(configUser.getName());
        Role role = this.roleRepository.findByType(configUser.getRole());
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setEmail(configUser.getEmail());
        user.setUserName(configUser.getUserName());

        User userEdited = this.userRepository.save(user);

        for(Subscription subscription : subscriptionsSubscriber) {
            subscription.setSubscriber(userEdited);

            this.subscriptionRepository.save(subscription);
        }

        for(Subscription subscription : subscriptionsProducer) {
            subscription.setProducer(userEdited);

            this.subscriptionRepository.save(subscription);
        }

        return userEdited;
    }

    @Override
    @Transactional(readOnly = false)
    public User block(String name) {
        User user = this.userRepository.findByNameIgnoreCase(name);
        user.setBlock(!user.getBlock());

        return this.userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = false)
    public String delete(String name) {
        User user = this.userRepository.findByNameIgnoreCase(name);

        this.subscriptionRepository.deleteAllBySubscriber(user);
        this.subscriptionRepository.deleteAllByProducer(user);
        this.profileRepository.delete(user.getProfile());
        this.userRepository.delete(user);

        return name;
    }

    @Override
    @Transactional(readOnly = false)
    public User changeGenericRole(String name) {
        User user = this.userRepository.findByNameIgnoreCase(name);
        String type = RoleEnum.GENERIC.getType().equals(user.getRoles().get(0).getType()) ? RoleEnum.CONSUMER.getType() : RoleEnum.GENERIC.getType();

        List<Role> roles = new ArrayList<>();
        Role role = this.roleRepository.findByType(type);
        roles.add(role);
        user.setRoles(roles);

        return this.userRepository.save(user);
    }
}
