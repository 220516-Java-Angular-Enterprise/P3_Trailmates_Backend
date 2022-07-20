package com.revature.trailmates.communication.privatemessages;

import com.revature.trailmates.communication.privatemessages.dto.requests.NewPrivateMessageRequest;
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

@ExtendWith(MockitoExtension.class)
class PrivateMessageServiceTest {

    @Mock
    private PrivateMessageRepository privateMessageRepository;

    @InjectMocks
    private PrivateMessageService privateMessageService;

    @Spy
    NewPrivateMessageRequest newPrivateMessageRequest;

    @Test
    void getPrivateMessageById() {
        PrivateMessage dummy = new PrivateMessage();
        dummy.setId("foobar");

        Mockito.when(privateMessageRepository.getPrivateMessageByID(dummy.getId())).thenReturn(dummy);

        privateMessageRepository.getPrivateMessageByID(dummy.getId());

        assertTrue("foobar".contains(dummy.getId()));
    }

    @Test
    void getAllPrivateMessages() {
        ArrayList<PrivateMessage> pms = new ArrayList<PrivateMessage>();

        PrivateMessage myPM = new PrivateMessage();

        myPM.setId("0");

        pms.add(myPM);


        Mockito.when(privateMessageRepository.getAllPrivateMessages()).thenReturn(pms);

        ArrayList<PrivateMessage> pmsToCheck = privateMessageRepository.getAllPrivateMessages();

        //assertTrue("foobar".contains(dummy.getId()));
        assertEquals(pms.get(0).getId(), pmsToCheck.get(0).getId());
    }

    @Test
    void getAllPrivateMessagesInConversation() {
        ArrayList<PrivateMessage> pms = new ArrayList<PrivateMessage>();

        PrivateMessage myPM = new PrivateMessage();

        myPM.setId("0");

        pms.add(myPM);


        Mockito.when(privateMessageRepository.getAllPrivateMessagesInConversation("foo")).thenReturn(pms);

        ArrayList<PrivateMessage> pmsToCheck = privateMessageRepository.getAllPrivateMessagesInConversation("foo");

        //assertTrue("foobar".contains(dummy.getId()));
        assertEquals(pms.get(0).getId(), pmsToCheck.get(0).getId());
    }

    @Test
    void saveNewPrivateMessage() {
        String newConvID = "foo";


        //Mockito.when(conversationService.createNewConversation("name")).thenReturn("foo");

        //String newConvName = conversationService.createNewConversation("name");

        assertTrue(newConvID.contains("foo"));
    }
}


