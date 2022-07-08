package com.revature.trailmates.communication.ownedconversation;

import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
