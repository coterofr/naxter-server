package com.platform.naxterbackend.chat.repository;

import com.platform.naxterbackend.chat.model.Message;
import com.platform.naxterbackend.user.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    @Query("{ '$or':[ { '$and': [ { 'emitter.$id' : ?0 }, { 'receiver.$id' : ?1 } ] }, { '$and': [ { 'receiver.$id' : ?0 }, { 'emitter.$id' : ?1 } ] } ] }")
    List<Message> findByEmitterAndReceiverOrderByDate(String user1, String user2, Sort sort);
}
