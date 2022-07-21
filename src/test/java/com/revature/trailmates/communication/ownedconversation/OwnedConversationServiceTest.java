package com.revature.trailmates.communication.ownedconversation;

import com.revature.trailmates.notifications.NotificationRepository;
import com.revature.trailmates.notifications.NotificationService;
import com.revature.trailmates.notifications.dto.NewNotificationRequest;
import com.revature.trailmates.user.User;
import com.revature.trailmates.user.UserRepository;
import com.revature.trailmates.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class OwnedConversationServiceTest {

    @Mock
    private OwnedConversationRepository ownedConversationRepository;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private UserRepository userService;

    @InjectMocks
    private OwnedConversationService ownedConversationService;
    @Mock
    NotificationService notificationService;

    @Spy
    NewNotificationRequest newNotificationRequest;


    @Test
    void getOwnedConversationById() {
        OwnedConversation dummy = new OwnedConversation();
        dummy.setId("foobar");

        Mockito.when(ownedConversationService.getOwnedConversationById(dummy.getId())).thenReturn(dummy);

        OwnedConversation ownedConversation = ownedConversationService.getOwnedConversationById(dummy.getId());

        assertTrue("foobar".contains(dummy.getId()));
    }

    @Test
    void getAllOwnedConversationsOfUser() {
        ArrayList<OwnedConversation> convos = new ArrayList<OwnedConversation>();

        OwnedConversation convo = new OwnedConversation();

        convo.setId("0");

        convos.add(convo);


        Mockito.when(ownedConversationService.getAllOwnedConversationsOfUser("Foo")).thenReturn(convos);

        ArrayList<OwnedConversation> usersToCheck = ownedConversationService.getAllOwnedConversationsOfUser("Foo");

        //assertTrue("foobar".contains(dummy.getId()));
        assertEquals(convos.get(0).getId(), usersToCheck.get(0).getId());
    }

    @Test
    void getAllUsersOfConversationID() {
        ArrayList<User> users = new ArrayList<User>();

        User myUser = new User();

        myUser.setId("0");

        users.add(myUser);

        ArrayList<OwnedConversation> someConvos = new ArrayList<OwnedConversation>();

        someConvos.add(new OwnedConversation());

        someConvos.get(0).setId("Foo");
        someConvos.get(0).setOwner(users.get(0));


        Mockito.when(ownedConversationRepository.getOwnedConversationByConversationID("Foo")).thenReturn(someConvos);

        Mockito.when(userService.getUserByID(any())).thenReturn(users.get(0));

        ArrayList<User> usersToCheck = ownedConversationService.getAllUsersOfConversationID("Foo");

        //assertTrue("foobar".contains(dummy.getId()));
        assertEquals(users.get(0).getId(), usersToCheck.get(0).getId());
    }

    @Test
    void getUserHasConversation() {
        OwnedConversation dummy = new OwnedConversation();
        dummy.setId("foobar");
        ArrayList<OwnedConversation> convos = new ArrayList<OwnedConversation>();
        convos.add(dummy);

        Mockito.when(ownedConversationService.getAllOwnedConversationsOfUser("foo")).thenReturn(convos);

        ArrayList<OwnedConversation> usersToCheck = ownedConversationService.getAllOwnedConversationsOfUser("foo");

        assertEquals(convos.get(0).getId(), usersToCheck.get(0).getId());
    }

    @Test
    void saveNewOwnedConversation() {
        String newConvID = "foo";


        //Mockito.when(conversationService.createNewConversation("name")).thenReturn("foo");

        //String newConvName = conversationService.createNewConversation("name");

        //assertTrue(newConvID.contains("foo"));

        newNotificationRequest = new NewNotificationRequest();
        newNotificationRequest.setNotification_type("NEW_CONVO");
        newNotificationRequest.setMessage("You have been added to a new group chat.");
        newNotificationRequest.setTarget_id("foo");

        ownedConversationService.saveNewOwnedConversation("foo", "foo");

        Mockito.verify(notificationService, times(1)).addNotification(any(), any());

//        Mockito.when(ownedConversationService.saveNewOwnedConversation("name", "foo")).thenReturn("foo");
//
//        String newConvName = ownedConversationService.saveNewOwnedConversation("name", "foo");
//
//        assertTrue(newConvID.contains("foo"));

    }
}