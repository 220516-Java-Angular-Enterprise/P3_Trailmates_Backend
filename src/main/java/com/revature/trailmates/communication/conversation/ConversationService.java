package com.revature.trailmates.communication.conversation;

import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.UUID;

@Service
@Transactional
public class ConversationService {

    @Inject
    private final ConversationRepository conversationRepository;


    @Inject
    @Autowired
    public ConversationService(ConversationRepository conversationRepository) { this.conversationRepository = conversationRepository; }

    //region Query
    public Conversation getConversationById(String id) { return conversationRepository.getConversationByID(id); }

    public ArrayList<Conversation> getAllConversationsOfUser(String id) { return conversationRepository.getAllConversationsOfUser(id); }


    //endregion

    //region Save

    public String createNewConversation(String conversationName){
        String newConversationID = UUID.randomUUID().toString();
        conversationRepository.saveConversations(newConversationID, conversationName);
        return newConversationID;
    }

    //endregion

}
