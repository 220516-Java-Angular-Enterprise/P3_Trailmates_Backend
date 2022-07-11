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
    @PostMapping(value = "/new-conversation", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Conversation newConversationRequest(@RequestHeader("Authorization") String token, @RequestBody NewConversationRequest request){

        //Verify user
        Principal principal = tokenService.noTokenThrow(token);
        if (principal.getId() == null) throw new UnauthorizedException();

        String conversationID = conversationService.createNewConversation(request.getConversationName());
        System.out.println("Conversation: @@@@@ " + conversationID);

        ArrayList<String> usersToAddToConversation = request.getUserIDs();

        ownedConversationService.saveNewOwnedConversation(principal.getId(), conversationID);
        for(String user : usersToAddToConversation){
            ownedConversationService.saveNewOwnedConversation(user, conversationID);
        }

        return new Conversation(conversationID, request.getConversationName());
    }

    //endregion

    //region Exception Handlers
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Map<String, Object> handleUnauthorizedException(UnauthorizedException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 401);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return responseBody;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody Map<String, Object> handleAuthenticationException(AuthenticationException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 403);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return responseBody;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Map<String, Object> handleInvalidRequestException(InvalidRequestException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 404);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return responseBody;
    }

    /**
     * Catches any exceptions in other methods and returns status code 409 if
     * a ResourceConflictException occurs.
     * @param e The resource conflict request being thrown
     * @return A map containing the status code, error message, and timestamp of
     * when the error occurred.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody Map<String, Object> handleResourceConflictException(ResourceConflictException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 409);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return responseBody;
    }
    //endregion

}
