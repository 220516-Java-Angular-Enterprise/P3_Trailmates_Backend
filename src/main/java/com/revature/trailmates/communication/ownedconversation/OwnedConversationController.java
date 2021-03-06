package com.revature.trailmates.communication.ownedconversation;

import com.revature.trailmates.user.User;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.util.custom_exception.UnauthorizedException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/owned-conversation")
public class OwnedConversationController {
    @Inject
    private final OwnedConversationService ownedConversationService;
    @Inject
    private final TokenService tokenService;

    @Inject
    @Autowired
    public OwnedConversationController(OwnedConversationService ownedConversationService, TokenService tokenService){
        this.ownedConversationService = ownedConversationService;
        this.tokenService = tokenService;
    }

    /**
     * Gets all the conversations the logged in user owns.
     * @param token authorization token to get user ID.
     * @return all owned convos to the client
     */
    @CrossOrigin
    @GetMapping(value = "/active")
    public @ResponseBody ArrayList<OwnedConversation> getAllMyActiveChats(@RequestHeader("Authorization") String token){
        Principal principal = tokenService.noTokenThrow(token);

        return ownedConversationService.getAllOwnedConversationsOfUser(principal.getId());
    }

    /**
     * Gets all the people that are in a chat.
     * @param token  Assures the user is logged in before doing their request.
     * @param chatid The ID of the conversation
     * @return all peeps in chat to the client
     */
    @CrossOrigin
    @GetMapping(value = "/active-in-chat/{chatid}")
    public @ResponseBody ArrayList<User> getAllUsersInChat(@RequestHeader("Authorization") String token, @PathVariable String chatid){
        Principal principal = tokenService.noTokenThrow(token);

        //If logged in user DOES NOT have access
        if (!ownedConversationService.getUserHasConversation(principal.getId(), chatid)) {
            throw new UnauthorizedException("Logged in user doesn't have access to conversation.");
        }

        return ownedConversationService.getAllUsersOfConversationID(chatid);
    }
}
