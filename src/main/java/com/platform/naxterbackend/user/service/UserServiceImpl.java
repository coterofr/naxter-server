package com.platform.naxterbackend.user.service;

import com.platform.naxterbackend.auth.jwt.JwtProvider;
import com.platform.naxterbackend.auth.model.JwtToken;
import com.platform.naxterbackend.auth.model.LoginUser;
import com.platform.naxterbackend.auth.model.RegisterUser;
import com.platform.naxterbackend.chat.model.Chat;
import com.platform.naxterbackend.chat.model.Message;
import com.platform.naxterbackend.chat.repository.ChatRepository;
import com.platform.naxterbackend.chat.repository.MessageRepository;
import com.platform.naxterbackend.comment.model.Comment;
import com.platform.naxterbackend.comment.repository.CommentRepository;
import com.platform.naxterbackend.post.model.Post;
import com.platform.naxterbackend.post.model.Tag;
import com.platform.naxterbackend.post.repository.PostRepository;
import com.platform.naxterbackend.post.repository.TagRepository;
import com.platform.naxterbackend.profile.model.Profile;
import com.platform.naxterbackend.profile.repository.ProfileRepository;
import com.platform.naxterbackend.subscription.model.Subscription;
import com.platform.naxterbackend.subscription.repository.SubscriptionRepository;
import com.platform.naxterbackend.theme.model.Theme;
import com.platform.naxterbackend.theme.repository.ThemeRepository;
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
    private ThemeRepository themeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;
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

        User user = new User(registerUser.getName(), registerUser.getEmail(), registerUser.getUserName(), passwordEncoded,
                             Boolean.FALSE, new BigDecimal(0.0), roles, profileSaved, null);

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = false)
    public JwtToken login(LoginUser loginUser, Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = this.jwtProvider.generateJwtToken(authentication);

        return new JwtToken(jwtToken);
    }

    private void updateSubscriptions(List<Subscription> subscriptionsSubscriber,
                                     List<Subscription> subscriptionsProducer,
                                     User user) {
        for(Subscription subscription : subscriptionsSubscriber) {
            subscription.setSubscriber(user);

            this.subscriptionRepository.save(subscription);
        }

        for(Subscription subscription : subscriptionsProducer) {
            subscription.setProducer(user);

            this.subscriptionRepository.save(subscription);
        }
    }

    private void updateThemes(List<Theme> themes,
                              User user) {
        for(Theme theme : themes) {
            theme.setUser(user);

            this.themeRepository.save(theme);
        }
    }

    private void updatePosts(List<Post> posts,
                             User user) {
        for(Post post : posts) {
            post.setUser(user);

            this.postRepository.save(post);
        }
    }

    private void updateComments(List<Comment> comments,
                                User user) {
        for(Comment comment : comments) {
            comment.setUser(user);

            this.commentRepository.save(comment);
        }
    }

    private void updateMessages(List<Message> messagesEmitter,
                                List<Message> messagesReceiver,
                                User user) {
        for(Message message : messagesEmitter) {
            message.setEmitter(user);

            this.messageRepository.save(message);
        }

        for(Message message : messagesReceiver) {
            message.setReceiver(user);

            this.messageRepository.save(message);
        }
    }

    private void updateChats(List<Chat> chatsUser1,
                             List<Chat> chatsUser2,
                             User user) {
        for(Chat chat : chatsUser1) {
            chat.setUser1(user);

            this.chatRepository.save(chat);
        }

        for(Chat chat : chatsUser2) {
            chat.setUser2(user);

            this.chatRepository.save(chat);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public User edit(String name, ConfigUser configUser) {
        User user = this.userRepository.findByNameIgnoreCase(name);

        List<Subscription> subscriptionsSubscriber = new ArrayList<>();
        List<Subscription> subscriptionsProducer = new ArrayList<>();
        List<Theme> themes = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        List<Message> messagesEmitter = new ArrayList<>();
        List<Message> messagesReceiver = new ArrayList<>();
        List<Chat> chatsUser1 = new ArrayList<>();
        List<Chat> chatsUser2 = new ArrayList<>();
        if(Boolean.FALSE.equals(name.equals(configUser.getName()))) {
            subscriptionsSubscriber = this.subscriptionRepository.findAllBySubscriber(user);
            subscriptionsProducer = this.subscriptionRepository.findAllByProducer(user);
            themes = this.themeRepository.findAllByUser(user);
            posts = this.postRepository.findAllByUser(user);
            comments = this.commentRepository.findAllByUser(user);
            messagesEmitter = this.messageRepository.findAllByEmitter(user);
            messagesReceiver = this.messageRepository.findAllByReceiver(user);
            chatsUser1 = this.chatRepository.findAllByUser1(user);
            chatsUser2 = this.chatRepository.findAllByUser2(user);

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

        if(Boolean.FALSE.equals(name.equals(configUser.getName()))) {
            this.updateSubscriptions(subscriptionsSubscriber, subscriptionsProducer, userEdited);
            this.updateThemes(themes, userEdited);
            this.updatePosts(posts, userEdited);
            this.updateComments(comments, userEdited);
            this.updateMessages(messagesEmitter, messagesReceiver, userEdited);
            this.updateChats(chatsUser1, chatsUser2, userEdited);
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

        List<Post> posts = this.postRepository.findAllByUser(user);
        for(Post post : posts) {
            for(Tag tag : post.getTags()) {
                this.tagRepository.delete(tag);
            }

            this.commentRepository.deleteAllByPost(post);
        }

        this.postRepository.deleteAllByUser(user);

        this.messageRepository.deleteAllByEmitter(user);
        this.messageRepository.deleteAllByReceiver(user);
        this.chatRepository.deleteAllByUser1(user);
        this.chatRepository.deleteAllByUser2(user);

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
