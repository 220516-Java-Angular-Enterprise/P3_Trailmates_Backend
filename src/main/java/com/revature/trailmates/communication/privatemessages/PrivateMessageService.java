package com.revature.trailmates.communication.privatemessages;

import com.revature.trailmates.communication.privatemessages.dto.requests.NewPrivateMessageRequest;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.UUID;

@Service
@Transactional
public class PrivateMessageService {

    @Inject
    private final PrivateMessageRepository privateMessageRepository;


    @Inject
    @Autowired
    public PrivateMessageService(PrivateMessageRepository privateMessageRepository) { this.privateMessageRepository = privateMessageRepository; }

    //region Query
    public PrivateMessage getPrivateMessageById(String id) { return privateMessageRepository.getPrivateMessageByID(id); }

    public ArrayList<PrivateMessage> getAllPrivateMessages() { return privateMessageRepository.getAllPrivateMessages(); }

    public ArrayList<PrivateMessage> getAllPrivateMessagesInConversation( String conversationID ) { return privateMessageRepository.getAllPrivateMessagesInConversation(conversationID); }

    public PrivateMessage saveNewPrivateMessage(NewPrivateMessageRequest request) {
        UUID newUUID = UUID.randomUUID();
        return privateMessageRepository.saveNewPrivateMessage(newUUID.toString(), request.getMessage(), request.getTime_sent(), request.getSender_id(), request.getConversation_id());
    }
    //endregion


}
