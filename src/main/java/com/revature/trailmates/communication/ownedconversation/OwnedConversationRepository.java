package com.revature.trailmates.communication.ownedconversation;

import com.revature.trailmates.communication.Conversation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OwnedConversationRepository extends CrudRepository<OwnedConversation, String> {
    @Query(value = "SELECT * FROM owned_conversations WHERE id = ?1", nativeQuery = true)
    OwnedConversation getOwnedConversationByID(String id);

    @Query(value = "SELECT * FROM owned_conversations WHERE owner = ?1 INNER JOIN conversations ON conversation = id INNER JOIN private_messages ON conversation = conversation ORDER BY time_sent", nativeQuery = true)
    ArrayList<OwnedConversation> getAllOwnedConversationsOfUser(String userID);
}
