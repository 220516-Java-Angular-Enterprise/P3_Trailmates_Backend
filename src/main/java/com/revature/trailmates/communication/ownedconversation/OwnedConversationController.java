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

    @GetMapping(value = "/active")
    public @ResponseBody ArrayList<OwnedConversation> getAllUsers(@RequestHeader("Authorization") String token){
        Principal principal = tokenService.noTokenThrow(token);
        if (principal.getId() == null) throw new UnauthorizedException();

        return ownedConversationService.getAllOwnedConversationsOfUser(principal.getId());
    }

    @GetMapping(value = "/active-in-chat/{chatid}")
    public @ResponseBody ArrayList<User> getAllUsers(@RequestHeader("Authorization") String token, @PathVariable String chatid){
        Principal principal = tokenService.noTokenThrow(token);
        if (principal.getId() == null) throw new UnauthorizedException();

        return ownedConversationService.getAllUsersOfConversationID(chatid);
    }
}
