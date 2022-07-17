package com.revature.trailmates.communication.privatemessages;


import com.revature.trailmates.communication.privatemessages.PrivateMessage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public interface PrivateMessageRepository extends CrudRepository<PrivateMessage, String> {

    //<editor-fold desc="Query">

    @Query(value = "SELECT * FROM private_messages WHERE id = ?1", nativeQuery = true)
    PrivateMessage getPrivateMessageByID(String id);

    @Query(value = "SELECT * FROM private_messages", nativeQuery = true)
    ArrayList<PrivateMessage> getAllPrivateMessages();

    @Query(value = "SELECT * FROM private_messages WHERE conversation = ?1 ORDER BY time_sent", nativeQuery = true)
    ArrayList<PrivateMessage> getAllPrivateMessagesInConversation(String conversation);

    //</editor-fold desc="Query>

    //<editor-fold desc="Save">
    @Modifying
    @Query(value = "INSERT INTO private_messages (id, message, time_sent, sender_id, conversation) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    void saveNewPrivateMessage(String uuid, String message, Timestamp time_sent, String sender_id, String conversation_id);

    //</editor-fold desc="Save">
    
}