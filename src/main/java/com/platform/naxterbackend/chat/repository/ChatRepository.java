package com.platform.naxterbackend.chat.repository;

import com.platform.naxterbackend.chat.model.Chat;
import com.platform.naxterbackend.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    Boolean existsByUser1AndUser2(User user1, User user2);

    Chat findByUser1AndUser2(User user1, User user2);
}
