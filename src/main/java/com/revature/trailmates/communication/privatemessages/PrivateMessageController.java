package com.revature.trailmates.communication.privatemessages;

import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;

import com.revature.trailmates.communication.ownedconversation.OwnedConversationService;
import com.revature.trailmates.communication.privatemessages.dto.requests.NewPrivateMessageRequest;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.AuthenticationException;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import com.revature.trailmates.util.custom_exception.UnauthorizedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/private-message")
public class PrivateMessageController {
    @Inject
    private final PrivateMessageService privateMessageService;
    @Inject
    private final TokenService tokenService;
    @Inject
    private final OwnedConversationService ownedConversationService;
    @Inject
    @Autowired
    public PrivateMessageController(PrivateMessageService privateMessageService, TokenService tokenService, OwnedConversationService ownedConversationService){
        this.privateMessageService = privateMessageService;
        this.tokenService = tokenService;
        this.ownedConversationService = ownedConversationService;
    }

    /**
     * This will retrieve all the messages in a conversation and order them by newest to oldest. index 0 -> N
     * @param conversation ID of the Conversation from the 'conversations' table.
     * @param token Authorization token used to retrieve the logged in user to make sure he's logged in.
     * @return all pms in a chat to the client
     */
    @CrossOrigin
    @GetMapping(value = "/conversation/{conversation}")
    public @ResponseBody ArrayList<PrivateMessage> getConversationsOfUser(@PathVariable String conversation, @RequestHeader("Authorization") String token){
        Principal principal = tokenService.noTokenThrow(token);

        //If logged in user DOES NOT have access
        if (!ownedConversationService.getUserHasConversation(principal.getId(), conversation)) {
            throw new UnauthorizedException("Logged in user doesn't have access to conversation.");
        }

        return privateMessageService.getAllPrivateMessagesInConversation(conversation);
    }

    /**
     * Use this method when you intend to send a private message. This will save it into the database taking the sender by token &
     * it will take the message, time sent, & conversation by the NewPrivateMessageRequest request.
     * @param token assures user is logged in
     * @param request takes in a NewPrivateMessageRequest packet.
     * @return the UUID of the private message.
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String saveNewPrivateMessage(@RequestHeader("Authorization") String token, @RequestBody NewPrivateMessageRequest request){
        Principal principal = tokenService.noTokenThrow(token);

        //If logged in user DOES NOT have access
        if (!ownedConversationService.getUserHasConversation(principal.getId(), request.getConversation_id())) {
            throw new UnauthorizedException("Logged in user doesn't have access to conversation.");
        }

        return privateMessageService.saveNewPrivateMessage(principal.getId(), request);
    }



}
