package com.revature.trailmates.communication.ownedconversation;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OwnedConversationRepository extends CrudRepository<OwnedConversation, String> {
    @Query(value = "SELECT * FROM owned_conversations WHERE id = ?1", nativeQuery = true)
    OwnedConversation getOwnedConversationByID(String id);

    @Query(value = "SELECT * FROM owned_conversations WHERE conversation = ?1", nativeQuery = true)
    ArrayList<OwnedConversation> getOwnedConversationByConversationID(String id);

    @Query(value = "SELECT * FROM owned_conversations ocv WHERE ocv.owner = ?1", nativeQuery = true)
    ArrayList<OwnedConversation> getAllOwnedConversationsOfUser(String userID); // INNER JOIN conversations cv ON ocv.conversation = cv.id INNER JOIN private_messages pm ON ocv.conversation = pm.conversation ORDER BY pm.time_sent

    @Query(value = "SELECT * FROM owned_conversations WHERE owner = ?1 AND conversation = ?2", nativeQuery = true)
    ArrayList<OwnedConversation> getOwnerHasConversation(String ownerID, String conversationID);

    @Modifying
    @Query(value = "INSERT INTO owned_conversations (id, conversation, owner) VALUES (?1, ?2, ?3)", nativeQuery = true)
    public void saveNewOwnedConversation(String id, String conversationID, String userID);
}
