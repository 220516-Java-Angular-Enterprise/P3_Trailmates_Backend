package com.revature.trailmates.communication.ownedconversation;

import com.revature.trailmates.user.User;
import com.revature.trailmates.user.UserService;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.UUID;

@Service
@Transactional
public class OwnedConversationService {

    @Inject
    private final OwnedConversationRepository ownedConversationRepository;
    @Inject
    private final UserService userService;

    @Inject
    @Autowired
    public OwnedConversationService(OwnedConversationRepository ownedConversationRepository, UserService userService){
        this.ownedConversationRepository = ownedConversationRepository;
        this.userService = userService;
    }

    //region Query
    public OwnedConversation getOwnedConversationById(String id) { return ownedConversationRepository.getOwnedConversationByID(id); }

    public ArrayList<OwnedConversation> getAllOwnedConversationsOfUser(String id) { return ownedConversationRepository.getAllOwnedConversationsOfUser(id); }

    public ArrayList<User> getAllUsersOfConversationID(String conversationID){
        ArrayList<OwnedConversation> peopleInChat = ownedConversationRepository.getOwnedConversationByConversationID(conversationID);
        ArrayList<User> users = new ArrayList<User>();
        for(OwnedConversation oC : peopleInChat){
            User userById = userService.getUserById(oC.getOwner().getId());
            users.add(userById);
        }
        return users;
    }

    public boolean getUserHasConversation (String userID, String conversationID) {
        ArrayList<OwnedConversation> conversations = ownedConversationRepository.getOwnerHasConversation(userID, conversationID);
        System.out.println("USER: " + userID + " has conversation count: " + conversations.size());
        if (conversations.size() > 0) {
            return true;
        }
        return false;
    }

    //endregion

    //region save

    public void saveNewOwnedConversation(String userID, String conversationID){
        String newID = UUID.randomUUID().toString();
        ownedConversationRepository.saveNewOwnedConversation(newID, conversationID, userID);
    }

    //endregion

}
