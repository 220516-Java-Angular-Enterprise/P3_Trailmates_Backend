package com.revature.trailmates.communication.conversation;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, String> {

    //<editor-fold desc="Query">

    @Query(value = "SELECT * FROM conversations WHERE id = ?1", nativeQuery = true)
    Conversation getConversationByID(String id);

    @Query(value = "SELECT * FROM conversations cv WHERE owner = ?1 INNER JOIN private_messages pm ON cv.id = pm.conversation ORDER BY pm.time_sent", nativeQuery = true)
    ArrayList<Conversation> getAllConversationsOfUser(String userID);// INNER JOIN private_messages pm ON cv.id = pm.conversation ORDER BY pm.time_sent
    //</editor-fold desc="Query>

    //<editor-fold desc="Save">
    @Modifying
    @Query(value = "INSERT INTO conversations (id, name) VALUES (?1, ?2)", nativeQuery = true)
    public void saveConversations(String id, String name);
    //</editor-fold desc="Save">

    //<editor-fold desc="Update PrivateMessage">
//    @Modifying
//    @Query(value = "UPDATE Conversations SET email = ?1, bio = ?2, age = ?3 WHERE id = ?4", nativeQuery = true)
//    void updatePrivateMessage(String email, String bio, int age, String id);
    //</editor-fold desc="Update PrivateMessage">

}
