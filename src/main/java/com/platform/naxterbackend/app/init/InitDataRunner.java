package com.platform.naxterbackend.app.init;

import com.platform.naxterbackend.chat.model.Chat;
import com.platform.naxterbackend.chat.model.Message;
import com.platform.naxterbackend.chat.repository.ChatRepository;
import com.platform.naxterbackend.chat.repository.MessageRepository;
import com.platform.naxterbackend.comment.model.Comment;
import com.platform.naxterbackend.comment.repository.CommentRepository;
import com.platform.naxterbackend.merchandising.model.Item;
import com.platform.naxterbackend.merchandising.model.Merchandising;
import com.platform.naxterbackend.merchandising.repository.ItemRepository;
import com.platform.naxterbackend.merchandising.repository.MerchandisingRepository;
import com.platform.naxterbackend.post.model.Post;
import com.platform.naxterbackend.post.model.Tag;
import com.platform.naxterbackend.post.repository.PostRepository;
import com.platform.naxterbackend.post.repository.TagRepository;
import com.platform.naxterbackend.profile.model.Profile;
import com.platform.naxterbackend.profile.repository.ProfileRepository;
import com.platform.naxterbackend.subscription.repository.SubscriptionRepository;
import com.platform.naxterbackend.theme.model.Theme;
import com.platform.naxterbackend.theme.repository.ThemeRepository;
import com.platform.naxterbackend.user.model.Role;
import com.platform.naxterbackend.user.model.RoleEnum;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.repository.RoleRepository;
import com.platform.naxterbackend.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class InitDataRunner implements ApplicationRunner {

    private final static Logger logger = LoggerFactory.getLogger(InitDataRunner.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
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
    private MerchandisingRepository merchandisingRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) {
        try {


            /* INIT DATA MERCHANDISING */


            this.itemRepository.deleteAll();
            this.merchandisingRepository.deleteAll();

            Item item1 = new Item("Item 1", "Descripción del Item 1", null);

            Item item1Saved = this.itemRepository.save(item1);

            List<Item> items = new ArrayList<>();
            items.add(item1Saved);

            Merchandising merchandising1 = new Merchandising("Merchandising 1", "Descripción del Merchandising 1", items);
            Merchandising merchandising2 = new Merchandising("Merchandising 2", "Descripción del Merchandising 2", new ArrayList<>());
            Merchandising merchandising3 = new Merchandising("Merchandising 3", "Descripción del Merchandising 3", new ArrayList<>());

            Merchandising merchandising1Saved = this.merchandisingRepository.save(merchandising1);
            Merchandising merchandising2Saved = this.merchandisingRepository.save(merchandising2);
            Merchandising merchandising3Saved = this.merchandisingRepository.save(merchandising3);


            /* INIT DATA PROFILES */


            this.roleRepository.deleteAll();
            this.profileRepository.deleteAll();
            this.userRepository.deleteAll();

            Profile profile1 = new Profile("Descripción del perfil del usuario 1 para la introducción de las características más destacables de este.",
                               new SimpleDateFormat("yyyy-MM-dd").parse("1995-05-15"), 1);
            Profile profile2 = new Profile("Descripción del perfil del usuario 2 para la introducción de las características más destacables de este.",
                               new SimpleDateFormat("yyyy-MM-dd").parse("1996-05-15"), 2);
            Profile profile3 = new Profile("Descripción del perfil del usuario 3 para la introducción de las características más destacables de este.",
                               new SimpleDateFormat("yyyy-MM-dd").parse("1997-05-15"), 3);
            Profile profile4 = new Profile("Descripción del perfil del usuario 4 para la introducción de las características más destacables de este.",
                               new SimpleDateFormat("yyyy-MM-dd").parse("1998-05-15"), 4);
            Profile profile5 = new Profile("Descripción del perfil del usuario 5 para la introducción de las características más destacables de este.",
                               new SimpleDateFormat("yyyy-MM-dd").parse("1999-05-15"), 5);
            Profile profile6 = new Profile("Descripción del perfil del usuario 6 para la introducción de las características más destacables de este.",
                               new SimpleDateFormat("yyyy-MM-dd").parse("2000-05-15"), 6);
            Profile profile7 = new Profile("Descripción del perfil del usuario 7 para la introducción de las características más destacables de este.",
                               new SimpleDateFormat("yyyy-MM-dd").parse("2001-05-15"), 7);
            Profile profile8 = new Profile("Descripción del perfil del usuario 8 para la introducción de las características más destacables de este.",
                               new SimpleDateFormat("yyyy-MM-dd").parse("2002-05-15"), 8);
            Profile profile9 = new Profile("Descripción del perfil del usuario 9 para la introducción de las características más destacables de este.",
                               new SimpleDateFormat("yyyy-MM-dd").parse("2003-05-15"), 9);
            Profile profile10 = new Profile("Descripción del perfil de Carlos Otero para la introducción de las características más destacables de este.",
                                new SimpleDateFormat("yyyy-MM-dd").parse("1997-07-22"), 0);

            Profile profile1Saved = this.profileRepository.save(profile1);
            Profile profile2Saved = this.profileRepository.save(profile2);
            Profile profile3Saved = this.profileRepository.save(profile3);
            Profile profile4Saved = this.profileRepository.save(profile4);
            Profile profile5Saved = this.profileRepository.save(profile5);
            Profile profile6Saved = this.profileRepository.save(profile6);
            Profile profile7Saved = this.profileRepository.save(profile7);
            Profile profile8Saved = this.profileRepository.save(profile8);
            Profile profile9Saved = this.profileRepository.save(profile9);
            Profile profile10Saved = this.profileRepository.save(profile10);


            /* INIT DATA ROLES */


            Role roleGeneric = new Role(RoleEnum.GENERIC.getType(), RoleEnum.GENERIC.getDescription());
            Role roleConsumer = new Role(RoleEnum.CONSUMER.getType(), RoleEnum.CONSUMER.getDescription());
            Role roleProducer = new Role(RoleEnum.PRODUCER.getType(), RoleEnum.PRODUCER.getDescription());
            Role roleModerator = new Role(RoleEnum.MODERATOR.getType(), RoleEnum.MODERATOR.getDescription());
            Role roleAdmin = new Role(RoleEnum.ADMIN.getType(), RoleEnum.ADMIN.getDescription());

            this.roleRepository.save(roleGeneric);
            this.roleRepository.save(roleConsumer);
            this.roleRepository.save(roleProducer);
            this.roleRepository.save(roleModerator);
            this.roleRepository.save(roleAdmin);

            List<Role> roles1 = new ArrayList<>(Arrays.asList(roleGeneric));
            List<Role> roles2 = new ArrayList<>(Arrays.asList(roleGeneric));
            List<Role> roles3 = new ArrayList<>(Arrays.asList(roleConsumer));
            List<Role> roles4 = new ArrayList<>(Arrays.asList(roleConsumer));
            List<Role> roles5 = new ArrayList<>(Arrays.asList(roleProducer));
            List<Role> roles6 = new ArrayList<>(Arrays.asList(roleProducer));
            List<Role> roles7 = new ArrayList<>(Arrays.asList(roleModerator));
            List<Role> roles8 = new ArrayList<>(Arrays.asList(roleModerator));
            List<Role> roles9 = new ArrayList<>(Arrays.asList(roleAdmin));
            List<Role> roles10 = new ArrayList<>(Arrays.asList(roleAdmin));


            /* INIT DATA USERS */


            User user1 = new User("user_1", "user_1@gmail.com", "User1 Surname", this.passwordEncoder.encode("12345"),
                         Boolean.FALSE, new BigDecimal(0.0), roles1, profile1Saved, null);
            User user2 = new User("user_2", "user_2@gmail.com", "User2 Surname", this.passwordEncoder.encode("12345"),
                         Boolean.FALSE, new BigDecimal(0.0), roles2, profile2Saved, null);
            User user3 = new User("user_3", "user_3@gmail.com", "User3 Surname", this.passwordEncoder.encode("12345"),
                         Boolean.FALSE, new BigDecimal(0.0), roles3, profile3Saved, null);
            User user4 = new User("user_4", "user_4@gmail.com", "User4 Surname", this.passwordEncoder.encode("12345"),
                         Boolean.FALSE, new BigDecimal(0.0), roles4, profile4Saved, null);
            User user5 = new User("user_5", "user_5@gmail.com", "User5 Surname", this.passwordEncoder.encode("12345"),
                         Boolean.FALSE, new BigDecimal(4.0), roles5, profile5Saved, null);
            User user6 = new User("user_6", "user_6@gmail.com", "User6 Surname", this.passwordEncoder.encode("12345"),
                         Boolean.FALSE, new BigDecimal(2.5), roles6, profile6Saved, null);
            User user7 = new User("user_7", "user_7@gmail.com", "User7 Surname", this.passwordEncoder.encode("12345"),
                         Boolean.FALSE, new BigDecimal(5.0), roles7, profile7Saved, merchandising1Saved);
            User user8 = new User("user_8", "user_8@gmail.com", "User8 Surname", this.passwordEncoder.encode("12345"),
                         Boolean.FALSE, new BigDecimal(6.0), roles8, profile8Saved, merchandising2Saved);
            User user9 = new User("user_9", "user_9@gmail.com", "User9 Surname", this.passwordEncoder.encode("12345"),
                         Boolean.FALSE, new BigDecimal(7.0), roles9, profile9Saved, null);
            User user10 = new User("carlos_otero", "carlos_otero@gmail.com", "Carlos Otero Franjo", this.passwordEncoder.encode("12345"),
                          Boolean.FALSE, new BigDecimal(9.5), roles10, profile10Saved, merchandising3Saved);

            User user1Saved = this.userRepository.save(user1);
            User user2Saved = this.userRepository.save(user2);
            User user3Saved = this.userRepository.save(user3);
            User user4Saved = this.userRepository.save(user4);
            User user5Saved = this.userRepository.save(user5);
            User user6Saved = this.userRepository.save(user6);
            User user7Saved = this.userRepository.save(user7);
            User user8Saved = this.userRepository.save(user8);
            User user9Saved = this.userRepository.save(user9);
            User user10Saved = this.userRepository.save(user10);


            /* INIT DATA SUBSCRIPTIONS */


            this.subscriptionRepository.deleteAll();


            /* INIT DATA POSTS */


            this.commentRepository.deleteAll();
            this.tagRepository.deleteAll();
            this.postRepository.deleteAll();
            this.themeRepository.deleteAll();

            Post post1 = new Post("post_1", "Descripción del Post 1 para resaltar el asunto y objetivos más destacables de este.",
                         BigInteger.ONE, new BigDecimal(3.0), Boolean.FALSE, null, user5Saved, null,
                         new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-15"), new ArrayList<>());
            Post post2 = new Post("post_2", "Descripción del Post 2 para resaltar el asunto y objetivos más destacables de este.",
                         BigInteger.ONE, new BigDecimal(5.0), Boolean.FALSE, null, user5Saved, null,
                         new SimpleDateFormat("yyyy-MM-dd").parse("2021-06-16"), new ArrayList<>());
            Post post3 = new Post("post_3", "Descripción del Post 3 para resaltar el asunto y objetivos más destacables de este.",
                         BigInteger.ONE, new BigDecimal(1.0), Boolean.FALSE, null, user6Saved, null,
                         new SimpleDateFormat("yyyy-MM-dd").parse("2021-07-17"), new ArrayList<>());
            Post post4 = new Post("post_4", "Descripción del Post 4 para resaltar el asunto y objetivos más destacables de este.",
                         BigInteger.ONE, new BigDecimal(4.0), Boolean.FALSE, null, user6Saved, null,
                         new SimpleDateFormat("yyyy-MM-dd").parse("2021-08-18"), new ArrayList<>());
            Post post5 = new Post("post_5", "Descripción del Post 5 para resaltar el asunto y objetivos más destacables de este.",
                         BigInteger.ONE, new BigDecimal(2.0), Boolean.FALSE, null, user7Saved, null,
                         new SimpleDateFormat("yyyy-MM-dd").parse("2021-09-19"), new ArrayList<>());
            Post post6 = new Post("post_6", "Descripción del Post 6 para resaltar el asunto y objetivos más destacables de este.",
                         BigInteger.ONE, new BigDecimal(8.0), Boolean.FALSE, null, user7Saved, null,
                         new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-15"), new ArrayList<>());
            Post post7 = new Post("post_7", "Descripción del Post 7 para resaltar el asunto y objetivos más destacables de este.",
                         BigInteger.ONE, new BigDecimal(6.0), Boolean.FALSE, null, user8Saved, null,
                         new SimpleDateFormat("yyyy-MM-dd").parse("2022-06-16"), new ArrayList<>());
            Post post8 = new Post("post_8", "Descripción del Post 8 para resaltar el asunto y objetivos más destacables de este.",
                         BigInteger.ONE, new BigDecimal(7.0), Boolean.FALSE, null, user9Saved, null,
                         new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-17"), new ArrayList<>());
            Post post9 = new Post("post_9", "Descripción del Post 9 para resaltar el asunto y objetivos más destacables de este.",
                         BigInteger.ONE, new BigDecimal(9.0), Boolean.FALSE, null, user10Saved, null,
                         new SimpleDateFormat("yyyy-MM-dd").parse("2022-08-18"), new ArrayList<>());
            Post post10 = new Post("post_10", "Descripción del Post 10 para resaltar el asunto y objetivos más destacables de este. Se trata de una publicación creada por Carlos Otero y que tiene como objetivo tener una de las mejores valoraciones de la plataforma Naxter.",
                          BigInteger.ONE, new BigDecimal(10.0), Boolean.FALSE, null, user10Saved, null,
                          new SimpleDateFormat("yyyy-MM-dd").parse("2022-09-19"), new ArrayList<>());


            /* INIT DATA TAGS */


            Tag tag1 = new Tag("tag_1", "Descripción del Tag 1");
            Tag tag2 = new Tag("tag_2", "Descripción del Tag 2");
            Tag tag3 = new Tag("tag_3", "Descripción del Tag 3");
            Tag tag4 = new Tag("tag_4", "Descripción del Tag 4");
            Tag tag5 = new Tag("tag_5", "Descripción del Tag 5");
            Tag tag6 = new Tag("tag_6", "Descripción del Tag 6");
            Tag tag7 = new Tag("tag_7", "Descripción del Tag 7");
            Tag tag8 = new Tag("tag_8", "Descripción del Tag 8");
            Tag tag9 = new Tag("tag_9", "Descripción del Tag 9");
            Tag tag10 = new Tag("tag_10", "Descripción del Tag 10");

            Tag tag1Saved = this.tagRepository.save(tag1);
            Tag tag2Saved = this.tagRepository.save(tag2);
            Tag tag3Saved = this.tagRepository.save(tag3);
            Tag tag4Saved = this.tagRepository.save(tag4);
            Tag tag5Saved = this.tagRepository.save(tag5);
            Tag tag6Saved = this.tagRepository.save(tag6);
            Tag tag7Saved = this.tagRepository.save(tag7);
            Tag tag8Saved = this.tagRepository.save(tag8);
            Tag tag9Saved = this.tagRepository.save(tag9);
            Tag tag10Saved = this.tagRepository.save(tag10);

            List<Tag> tags1 = new ArrayList<>();
            tags1.add(tag1Saved);
            tags1.add(tag2Saved);
            tags1.add(tag3Saved);
            tags1.add(tag4Saved);
            List<Tag> tags2 = new ArrayList<>();
            tags2.add(tag5Saved);
            tags2.add(tag6Saved);
            tags2.add(tag7Saved);
            List<Tag> tags3 = new ArrayList<>();
            tags3.add(tag8Saved);
            List<Tag> tags4 = new ArrayList<>();
            tags4.add(tag9Saved);
            List<Tag> tags5 = new ArrayList<>();
            tags5.add(tag10Saved);

            post1.setTags(tags1);
            post2.setTags(tags2);
            post3.setTags(tags3);
            post4.setTags(tags4);
            post5.setTags(tags5);


            /* INIT DATA THEMES */


            Theme theme1 = new Theme("theme_1", "Descripción del Theme 1", user9Saved);
            Theme theme2 = new Theme("theme_2", "Descripción del Theme 2", user10Saved);
            Theme theme3 = new Theme("theme_3", "Descripción del Theme 3", user10Saved);

            Theme theme1Saved =this.themeRepository.save(theme1);
            Theme theme2Saved =this.themeRepository.save(theme2);
            Theme theme3Saved =this.themeRepository.save(theme3);

            post1.setTheme(theme1Saved);
            post2.setTheme(theme2Saved);
            post3.setTheme(theme3Saved);
            post4.setTheme(theme3Saved);
            post5.setTheme(theme3Saved);
            post6.setTheme(theme3Saved);
            post7.setTheme(theme3Saved);
            post8.setTheme(theme3Saved);
            post9.setTheme(theme3Saved);
            post10.setTheme(theme3Saved);

            Post post1Saved = this.postRepository.save(post1);
            Post post2Saved = this.postRepository.save(post2);
            Post post3Saved = this.postRepository.save(post3);
            Post post4Saved = this.postRepository.save(post4);
            Post post5Saved = this.postRepository.save(post5);
            Post post6Saved = this.postRepository.save(post6);
            Post post7Saved = this.postRepository.save(post7);
            Post post8Saved = this.postRepository.save(post8);
            Post post9Saved = this.postRepository.save(post9);
            Post post10Saved = this.postRepository.save(post10);


            /* INIT DATA COMMENTS */


            Comment comment1 = new Comment("Contenido del primer comentario de la publicación", user5Saved, post10Saved,
                               new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
            Comment comment2 = new Comment("Contenido del segundo comentario de la publicación", user5Saved, post10Saved,
                               new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-02"));
            Comment comment3 = new Comment("Contenido del tercer comentario de la publicación", user6Saved, post10Saved,
                               new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-03"));
            Comment comment4 = new Comment("Contenido del cuarto comentario de la publicación", user6Saved, post10Saved,
                               new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-04"));
            Comment comment5 = new Comment("Contenido del quinto comentario de la publicación", user7Saved, post10Saved,
                               new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-05"));
            Comment comment6 = new Comment("Contenido del sexto comentario de la publicación", user7Saved, post10Saved,
                               new SimpleDateFormat("yyyy-MM-dd").parse("2022-06-06"));
            Comment comment7 = new Comment("Contenido del séptimo comentario de la publicación", user8Saved, post10Saved,
                               new SimpleDateFormat("yyyy-MM-dd").parse("2022-07-07"));
            Comment comment8 = new Comment("Contenido del octavo comentario de la publicación", user9Saved, post10Saved,
                               new SimpleDateFormat("yyyy-MM-dd").parse("2022-08-08"));
            Comment comment9 = new Comment("Contenido del noveno comentario de la publicación", user10Saved, post10Saved,
                               new SimpleDateFormat("yyyy-MM-dd").parse("2022-09-09"));
            Comment comment10 = new Comment("Contenido del décimo comentario de la publicación", user10Saved, post10Saved,
                                new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-10"));

            this.commentRepository.save(comment1);
            this.commentRepository.save(comment2);
            this.commentRepository.save(comment3);
            this.commentRepository.save(comment4);
            this.commentRepository.save(comment5);
            this.commentRepository.save(comment6);
            this.commentRepository.save(comment7);
            this.commentRepository.save(comment8);
            this.commentRepository.save(comment9);
            this.commentRepository.save(comment10);


            /* INIT DATA CHATS */


            this.messageRepository.deleteAll();
            this.chatRepository.deleteAll();

            Message message1 = new Message(user10Saved, user9Saved, "Mensaje 1 de Carlos Otero",
                               new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:00:01"));
            Message message2 = new Message(user9Saved, user10Saved, "Mensaje 1 del Usuario 9",
                               new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:01:02"));
            Message message3 = new Message(user10Saved, user9Saved, "Mensaje 2 de Carlos Otero",
                               new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:02:03"));
            Message message4 = new Message(user9Saved, user10Saved, "Mensaje 2 del Usuario 9",
                               new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:03:04"));
            Message message5 = new Message(user10Saved, user9Saved, "Mensaje 3 de Carlos Otero",
                               new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:04:05"));
            Message message6 = new Message(user9Saved, user10Saved, "Mensaje 3 del Usuario 9",
                               new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:05:06"));
            Message message7 = new Message(user10Saved, user9Saved, "Mensaje 4 de Carlos Otero",
                               new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:06:07"));
            Message message8 = new Message(user9Saved, user10Saved, "Mensaje 4 del Usuario 9",
                               new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:07:08"));
            Message message9 = new Message(user10Saved, user9Saved, "Mensaje 5 de Carlos Otero",
                               new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:08:09"));
            Message message10 = new Message(user9Saved, user10Saved, "Mensaje 5 del Usuario 9",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:09:10"));
            Message message11 = new Message(user10Saved, user9Saved, "Mensaje 6 de Carlos Otero",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:10:11"));
            Message message12 = new Message(user9Saved, user10Saved, "Mensaje 6 del Usuario 9",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:11:12"));
            Message message13 = new Message(user10Saved, user9Saved, "Mensaje 7 de Carlos Otero",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:12:13"));
            Message message14 = new Message(user9Saved, user10Saved, "Mensaje 7 del Usuario 9",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:13:14"));
            Message message15 = new Message(user10Saved, user9Saved, "Mensaje 8 de Carlos Otero",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:14:15"));
            Message message16 = new Message(user9Saved, user10Saved, "Mensaje 8 del Usuario 9",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:15:16"));
            Message message17 = new Message(user10Saved, user9Saved, "Mensaje 9 de Carlos Otero",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:16:17"));
            Message message18 = new Message(user9Saved, user10Saved, "Mensaje 9  del Usuario 9",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 12:17:18"));
            Message message19 = new Message(user10Saved, user9Saved, "Mensaje 10 de Carlos Otero",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 13:18:19"));
            Message message20 = new Message(user9Saved, user10Saved, "Mensaje 10 del Usuario 9",
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2022-01-01 14:19:20"));

            Message message1Saved = this.messageRepository.save(message1);
            Message message2Saved = this.messageRepository.save(message2);
            Message message3Saved = this.messageRepository.save(message3);
            Message message4Saved = this.messageRepository.save(message4);
            Message message5Saved = this.messageRepository.save(message5);
            Message message6Saved = this.messageRepository.save(message6);
            Message message7Saved = this.messageRepository.save(message7);
            Message message8Saved = this.messageRepository.save(message8);
            Message message9Saved = this.messageRepository.save(message9);
            Message message10Saved = this.messageRepository.save(message10);
            Message message11Saved = this.messageRepository.save(message11);
            Message message12Saved = this.messageRepository.save(message12);
            Message message13Saved = this.messageRepository.save(message13);
            Message message14Saved = this.messageRepository.save(message14);
            Message message15Saved = this.messageRepository.save(message15);
            Message message16Saved = this.messageRepository.save(message16);
            Message message17Saved = this.messageRepository.save(message17);
            Message message18Saved = this.messageRepository.save(message18);
            Message message19Saved = this.messageRepository.save(message19);
            Message message20Saved = this.messageRepository.save(message20);

            List<Message> messages = new ArrayList<>();
            messages.add(message1Saved);
            messages.add(message2Saved);
            messages.add(message3Saved);
            messages.add(message4Saved);
            messages.add(message5Saved);
            messages.add(message6Saved);
            messages.add(message7Saved);
            messages.add(message8Saved);
            messages.add(message9Saved);
            messages.add(message10Saved);
            messages.add(message11Saved);
            messages.add(message12Saved);
            messages.add(message13Saved);
            messages.add(message14Saved);
            messages.add(message15Saved);
            messages.add(message16Saved);
            messages.add(message17Saved);
            messages.add(message18Saved);
            messages.add(message19Saved);
            messages.add(message20Saved);

            Chat chat = new Chat(user10Saved, user9Saved, messages);

            this.chatRepository.save(chat);

        } catch (Exception e) {
            logger.error("Error en los registros de las entidades: " + e.getMessage());
        }
    }
}
