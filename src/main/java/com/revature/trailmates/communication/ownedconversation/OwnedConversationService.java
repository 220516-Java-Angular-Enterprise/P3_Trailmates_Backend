package com.revature.trailmates.communication.ownedconversation;

import com.revature.trailmates.notifications.NotificationService;
import com.revature.trailmates.notifications.dto.NewNotificationRequest;
import com.revature.trailmates.user.User;
import com.revature.trailmates.user.UserRepository;
import com.revature.trailmates.user.UserService;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class OwnedConversationService {

    @Inject
    private final OwnedConversationRepository ownedConversationRepository;
    @Inject
    private final UserRepository userService;
    private final NotificationService notificationService;

    @Inject
    @Autowired
    public OwnedConversationService(OwnedConversationRepository ownedConversationRepository, UserRepository userService, NotificationService notificationService) {
        this.ownedConversationRepository = ownedConversationRepository;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    //region Query
    public OwnedConversation getOwnedConversationById(String id) { return ownedConversationRepository.getOwnedConversationByID(id); }

    public ArrayList<OwnedConversation> getAllOwnedConversationsOfUser(String id) { return ownedConversationRepository.getAllOwnedConversationsOfUser(id); }

    public ArrayList<User> getAllUsersOfConversationID(String conversationID){
        ArrayList<OwnedConversation> peopleInChat = ownedConversationRepository.getOwnedConversationByConversationID(conversationID);
        ArrayList<User> users = new ArrayList<User>();
        for(OwnedConversation oC : peopleInChat){
            User userById = userService.getUserByID(oC.getOwner().getId());
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

    public String saveNewOwnedConversation(String userID, String conversationID){
        String newID = UUID.randomUUID().toString();
        ownedConversationRepository.saveNewOwnedConversation(newID, conversationID, userID);

        //region notif
        // Add a new notification for when you get added to a new group
        NewNotificationRequest request1 = new NewNotificationRequest();
        request1.setNotification_type("NEW_CONVO");
        request1.setMessage("You have been added to a new group chat.");
        request1.setTarget_id(newID);
        notificationService.addNotification(request1, userID);
        //endregion

        return conversationID;
    }

    //endregion

}
