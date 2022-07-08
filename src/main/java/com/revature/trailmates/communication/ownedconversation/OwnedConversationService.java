package com.revature.trailmates.communication.ownedconversation;

import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class OwnedConversationService {

    @Inject
    private final OwnedConversationRepository ownedConversationRepository;

    @Inject
    @Autowired
    public OwnedConversationService(OwnedConversationRepository ownedConversationRepository){
        this.ownedConversationRepository = ownedConversationRepository;
    }

    //region Query
    public OwnedConversation getOwnedConversationById(String id) { return ownedConversationRepository.getOwnedConversationByID(id); }

    public ArrayList<OwnedConversation> getAllOwnedConversationsOfUser(String id) { return ownedConversationRepository.getAllOwnedConversationsOfUser(id); }
    //endregion

}
