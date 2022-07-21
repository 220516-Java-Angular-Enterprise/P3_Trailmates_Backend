package com.revature.trailmates.communication.conversation;

import com.revature.trailmates.communication.conversation.dtos.requests.NewConversationRequest;
import com.revature.trailmates.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConversationServiceTest {

    @Mock
    private ConversationRepository conversationRepository;

    @InjectMocks
    private ConversationService conversationService;

    @Spy
    NewConversationRequest newConversationRequest;


    @Test
    void getConversationById() {
        Conversation dummy = new Conversation();
        dummy.setId("foobar");

        Mockito.when(conversationRepository.getConversationByID(dummy.getId())).thenReturn(dummy);

        conversationService.getConversationById(dummy.getId());

        assertTrue("foobar".contains(dummy.getId()));
    }

    @Test
    void getAllConversationsOfUser() {
        ArrayList<Conversation> convos = new ArrayList<Conversation>();

        Conversation convo = new Conversation();

        convo.setId("0");

        convos.add(convo);

        Mockito.when(conversationRepository.getAllConversationsOfUser("Foo")).thenReturn(convos);

        ArrayList<Conversation> convosToCheck = conversationService.getAllConversationsOfUser("Foo");

        //assertTrue("foobar".contains(dummy.getId()));
        assertEquals(convos.get(0).getId(), convosToCheck.get(0).getId());
    }

    @Test
    void createNewConversation() {
        String newConvID = "foo";

        //Mockito.when(conversationRepository.saveConversations("name"));
        String newConvName = conversationService.createNewConversation("name");
        Mockito.verify(conversationRepository, times(1)).saveConversations(newConvName, "name");

    }
}

