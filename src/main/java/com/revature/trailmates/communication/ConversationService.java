package com.revature.trailmates.communication;

import com.revature.trailmates.communication.privatemessages.PrivateMessage;
import com.revature.trailmates.communication.privatemessages.PrivateMessageRepository;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

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

}
