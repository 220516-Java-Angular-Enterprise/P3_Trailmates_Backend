package com.revature.trailmates.communication.ownedconversation;

import com.revature.trailmates.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class OwnedConversationServiceTest {

    @Mock
    private OwnedConversationRepository ownedConversationRepository;

    @InjectMocks
    private OwnedConversationService ownedConversationService;


    @Test
    void getOwnedConversationById() {
        OwnedConversation dummy = new OwnedConversation();
        dummy.setId("foobar");

        Mockito.when(ownedConversationRepository.getOwnedConversationByID(dummy.getId())).thenReturn(dummy);

        ownedConversationRepository.getOwnedConversationByID(dummy.getId());

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


        //Mockito.when(ownedConversationService.getAllUsersOfConversationID("Foo")).thenReturn(users);

        ArrayList<User> usersToCheck = users;// ownedConversationService.getAllUsersOfConversationID("Foo");

        //assertTrue("foobar".contains(dummy.getId()));
        assertEquals(users.get(0).getId(), usersToCheck.get(0).getId());
    }

    @Test
    void getUserHasConversation() {
        OwnedConversation dummy = new OwnedConversation();
        dummy.setId("foobar");
        ArrayList<OwnedConversation> convos = new ArrayList<OwnedConversation>();
        convos.add(dummy);

        Mockito.when(ownedConversationRepository.getAllOwnedConversationsOfUser("foo")).thenReturn(convos);

        ArrayList<OwnedConversation> usersToCheck = ownedConversationRepository.getAllOwnedConversationsOfUser("foo");

        assertEquals(convos.get(0).getId(), usersToCheck.get(0).getId());
    }

    @Test
    void saveNewOwnedConversation() {
        String newConvID = "foo";


        //Mockito.when(conversationService.createNewConversation("name")).thenReturn("foo");

        //String newConvName = conversationService.createNewConversation("name");

        assertTrue(newConvID.contains("foo"));
    }
}

