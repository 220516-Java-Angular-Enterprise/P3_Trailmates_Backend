package com.revature.trailmates.communication.conversation;

import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.communication.conversation.dtos.requests.NewConversationRequest;
import com.revature.trailmates.communication.ownedconversation.OwnedConversationRepository;
import com.revature.trailmates.communication.ownedconversation.OwnedConversationService;
import com.revature.trailmates.util.annotations.*;
import com.revature.trailmates.util.custom_exception.AuthenticationException;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import com.revature.trailmates.util.custom_exception.UnauthorizedException;
//import com.revature.trailmates.communication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/conversation")
public class ConversationController {
    @Inject
    private final ConversationService conversationService;
    @Inject
    private final TokenService tokenService;
    @Inject
    private final OwnedConversationService ownedConversationService;

    @Inject
    @Autowired
    public ConversationController(ConversationService conversationService, TokenService tokenService, OwnedConversationService ownedConversationService){
        this.conversationService = conversationService;
        this.tokenService = tokenService;
        this.ownedConversationService = ownedConversationService;
    }

    //region Gets

//    @GetMapping(value = "/user-conversations")
//    public @ResponseBody ArrayList<Conversation> getConversationsOfUser(@RequestHeader("Authorization") String token){
//        Principal principal = tokenService.noTokenThrow(token);
//        if (principal.getId() == null) throw new UnauthorizedException();
//
//        return conversationService.getAllConversationsOfUser(principal.getId());
//    }

    //Expects NewConversationRequest json object

    /**
     * Creates a new conversation.
     * @param token   Requires user to be logged in.
     * @param request Takes in the name of the conversation & the users in the conversation.
     * @return the new convo to the client
     */
    @CrossOrigin
    @PostMapping(value = "/new-conversation", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Conversation newConversationRequest(@RequestHeader("Authorization") String token, @RequestBody NewConversationRequest request){

        //Verify user
        Principal principal = tokenService.noTokenThrow(token);

        String conversationID = conversationService.createNewConversation(request.getConversationName());
        //System.out.println("Conversation: @@@@@ " + conversationID);

        ArrayList<String> usersToAddToConversation = request.getUserIDs();

        ownedConversationService.saveNewOwnedConversation(principal.getId(), conversationID);
        for(String user : usersToAddToConversation){
            ownedConversationService.saveNewOwnedConversation(user, conversationID);
        }

        return new Conversation(conversationID, request.getConversationName());
    }

    /**
     * Used to add people to conversation after they have been started.
     * @param token Assures users are logged in
     * @param conversation  the ID of the conversation you want to add the user to
     * @param user          The ID of the user you want to add to an existing conversation.
     */
    @CrossOrigin
    @PostMapping(value = "/add-user-to-conversation/{conversation}/{user}")
    public @ResponseBody void newConversationRequest(@RequestHeader("Authorization") String token, @PathVariable("conversation") String conversation, @PathVariable("user") String user){
        //Verify user
        Principal principal = tokenService.noTokenThrow(token);

        //If logged in user DOES NOT have access
        if (!ownedConversationService.getUserHasConversation(principal.getId(), conversation)) {
            throw new UnauthorizedException("Logged in user doesn't have access to conversation.");
        }

        if (ownedConversationService.getUserHasConversation(user, conversation)) {
            throw new ResourceConflictException("User is already in the conversation!");
        }

        ownedConversationService.saveNewOwnedConversation(user, conversation);
    }

    //endregion


}
