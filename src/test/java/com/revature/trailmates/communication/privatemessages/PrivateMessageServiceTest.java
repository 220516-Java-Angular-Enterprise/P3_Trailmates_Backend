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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

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

        Mockito.when(privateMessageService.getPrivateMessageById(dummy.getId())).thenReturn(dummy);

        privateMessageService.getPrivateMessageById(dummy.getId());

        assertTrue("foobar".contains(dummy.getId()));
    }

    @Test
    void getAllPrivateMessages() {
        ArrayList<PrivateMessage> pms = new ArrayList<PrivateMessage>();

        PrivateMessage myPM = new PrivateMessage();

        myPM.setId("0");

        pms.add(myPM);


        Mockito.when(privateMessageService.getAllPrivateMessages()).thenReturn(pms);

        ArrayList<PrivateMessage> pmsToCheck = privateMessageService.getAllPrivateMessages();

        //assertTrue("foobar".contains(dummy.getId()));
        assertEquals(pms.get(0).getId(), pmsToCheck.get(0).getId());
    }

    @Test
    void getAllPrivateMessagesInConversation() {
        ArrayList<PrivateMessage> pms = new ArrayList<PrivateMessage>();

        PrivateMessage myPM = new PrivateMessage();

        myPM.setId("0");

        pms.add(myPM);


        Mockito.when(privateMessageService.getAllPrivateMessagesInConversation("foo")).thenReturn(pms);

        ArrayList<PrivateMessage> pmsToCheck = privateMessageService.getAllPrivateMessagesInConversation("foo");

        //assertTrue("foobar".contains(dummy.getId()));
        assertEquals(pms.get(0).getId(), pmsToCheck.get(0).getId());
    }

    @Test
    void saveNewPrivateMessage() {
        String newConvID = "foo";




        String newConvName = privateMessageService.saveNewPrivateMessage("name", newPrivateMessageRequest);
        Mockito.verify(privateMessageRepository, times(1)).saveNewPrivateMessage(any(), any(), any(), any(), any());
        //assertTrue(newConvID.contains("foo"));
    }
}