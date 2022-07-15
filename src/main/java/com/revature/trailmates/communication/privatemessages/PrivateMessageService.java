package com.revature.trailmates.communication.privatemessages;

import com.revature.trailmates.communication.privatemessages.dto.requests.NewPrivateMessageRequest;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.sql.Timestamp;
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

    public String saveNewPrivateMessage(String userID, NewPrivateMessageRequest request) {
        String newUUID = UUID.randomUUID().toString();
        privateMessageRepository.saveNewPrivateMessage(newUUID, request.getMessage(), new Timestamp(request.getTime_sent())/*(1000*60*60*24))*/, userID, request.getConversation_id());
        return newUUID;
    }
    //endregion


}
