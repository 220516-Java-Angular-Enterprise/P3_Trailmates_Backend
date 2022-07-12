package com.revature.trailmates.communication.privatemessages;

import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;

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
    @Autowired
    public PrivateMessageController(PrivateMessageService privateMessageService, TokenService tokenService){
        this.privateMessageService = privateMessageService;
        this.tokenService = tokenService;
    }

    /**
     * This will retrieve all the messages in a conversation and order them by newest to oldest. index 0 -> N
     * @param conversation ID of the Conversation from the 'conversations' table.
     * @param token Authorization token used to retrieve the logged in user to make sure he's logged in.
     * @return
     */
    @CrossOrigin
    @GetMapping(value = "/conversation/{conversation}")
    public @ResponseBody ArrayList<PrivateMessage> getConversationsOfUser(@PathVariable String conversation, @RequestHeader("Authorization") String token){
        Principal principal = tokenService.noTokenThrow(token);
        if (principal.getId() == null) throw new UnauthorizedException();

        return privateMessageService.getAllPrivateMessagesInConversation(conversation);
    }

    /**
     * Use this method when you intend to send a private message. This will save it into the database taking the sender by token &
     * it will take the message, time sent, & conversation by the NewPrivateMessageRequest request.
    */
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String saveNewPrivateMessage(@RequestHeader("Authorization") String token, @RequestBody NewPrivateMessageRequest request){
        Principal principal = tokenService.noTokenThrow(token);
        if (principal.getId() == null) throw new UnauthorizedException();

        return privateMessageService.saveNewPrivateMessage(principal.getId(), request);
    }


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
