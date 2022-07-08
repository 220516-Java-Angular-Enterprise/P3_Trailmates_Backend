package com.revature.trailmates.communication.ownedconversation;

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
    @Autowired
    public OwnedConversationController(OwnedConversationService ownedConversationService){
        this.ownedConversationService = ownedConversationService;
    }

    @GetMapping(value = "/active")
    public @ResponseBody ArrayList<OwnedConversation> getAllUsers(@RequestHeader("Authorization") String token){
        Principal principal = new TokenService().extractRequesterDetails(token);
        if (principal.getId() == null) throw new UnauthorizedException();

        return ownedConversationService.getAllOwnedConversationsOfUser(principal.getId());
    }
}
