package com.platform.naxterbackend.profile.service;

import com.platform.naxterbackend.chat.model.Chat;
import com.platform.naxterbackend.chat.model.Message;
import com.platform.naxterbackend.chat.repository.ChatRepository;
import com.platform.naxterbackend.chat.repository.MessageRepository;
import com.platform.naxterbackend.comment.model.Comment;
import com.platform.naxterbackend.comment.repository.CommentRepository;
import com.platform.naxterbackend.merchandising.model.Cart;
import com.platform.naxterbackend.merchandising.model.Merchandising;
import com.platform.naxterbackend.merchandising.model.Product;
import com.platform.naxterbackend.merchandising.repository.CartRepository;
import com.platform.naxterbackend.merchandising.repository.MerchandisingRepository;
import com.platform.naxterbackend.merchandising.repository.ProductRepository;
import com.platform.naxterbackend.post.model.Post;
import com.platform.naxterbackend.post.repository.PostRepository;
import com.platform.naxterbackend.profile.model.Account;
import com.platform.naxterbackend.profile.model.Profile;
import com.platform.naxterbackend.profile.model.Visualization;
import com.platform.naxterbackend.profile.repository.ProfileRepository;
import com.platform.naxterbackend.subscription.model.Subscription;
import com.platform.naxterbackend.subscription.repository.SubscriptionRepository;
import com.platform.naxterbackend.theme.model.Theme;
import com.platform.naxterbackend.theme.repository.ThemeRepository;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private MerchandisingRepository merchandisingRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;


    @Override
    public List<Profile> getProfiles() {
        return this.profileRepository.findAll();
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

    private void updateMerchandisings(List<Merchandising> merchandisings,
                                      List<Product> products,
                                      List<Cart> carts,
                                      User user) {
        for(Merchandising merchandising : merchandisings) {
            merchandising.setUser(user);

            this.merchandisingRepository.save(merchandising);
        }

        for(Product product : products) {
            product.setUser(user);

            this.productRepository.save(product);
        }

        for(Cart cart : carts) {
            cart.setBuyer(user);

            this.cartRepository.save(cart);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public User editAccount(String name, Account account) {
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
        List<Merchandising> merchandisings = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        List<Cart> carts = new ArrayList<>();
        if(Boolean.FALSE.equals(name.equals(account.getName()))) {
            subscriptionsSubscriber = this.subscriptionRepository.findAllBySubscriber(user);
            subscriptionsProducer = this.subscriptionRepository.findAllByProducer(user);
            themes = this.themeRepository.findAllByUser(user);
            posts = this.postRepository.findAllByUser(user);
            comments = this.commentRepository.findAllByUser(user);
            messagesEmitter = this.messageRepository.findAllByEmitter(user);
            messagesReceiver = this.messageRepository.findAllByReceiver(user);
            chatsUser1 = this.chatRepository.findAllByUser1(user);
            chatsUser2 = this.chatRepository.findAllByUser2(user);
            merchandisings = this.merchandisingRepository.findAllByUser(user);
            products = this.productRepository.findAllByUser(user);
            carts = this.cartRepository.findAllByBuyer(user);

            this.userRepository.delete(user);
        }

        user.setName(account.getName());
        user.setEmail(account.getEmail());
        user.setUserName(account.getUserName());

        User userEdited = this.userRepository.save(user);

        if(Boolean.FALSE.equals(name.equals(account.getName()))) {
            this.updateSubscriptions(subscriptionsSubscriber, subscriptionsProducer, userEdited);
            this.updateThemes(themes, userEdited);
            this.updatePosts(posts, userEdited);
            this.updateComments(comments, userEdited);
            this.updateMessages(messagesEmitter, messagesReceiver, userEdited);
            this.updateChats(chatsUser1, chatsUser2, userEdited);
            this.updateMerchandisings(merchandisings, products, carts, user);
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
