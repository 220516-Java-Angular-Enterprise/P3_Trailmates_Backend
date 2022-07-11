package com.revature.trailmates.friends;

import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.AuthenticationException;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import com.revature.trailmates.util.custom_exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friends")
public class FriendController {

    @Inject
    private final FriendService friendService;

    @Autowired
    private TokenService tokenService;

    @Inject
    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    /**
     * Adds a Friend to the Database
     * @param friend_id The id of the friend the user is adding
     * @param token The Token of the current User
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{friend_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void addFriend(@PathVariable("friend_id") String friend_id, @RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        friendService.addNewFriend(user.getId(), friend_id);
    }

    /**
     * Returns a List of all the Friends a User has
     * @param token Token of the current User
     * @return A List of All the Users Friends
     */
    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Friend> getAllFriendsFromUser(@RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        return friendService.getAllFriendsFromUser(user.getId());
    }

    /**
     * Returns a List of all users who have current user as their friend but the current user doesn't have
     * as their friend.
     * @param token Token of the Current User
     * @return List of Pending Friends
     */
    @CrossOrigin
    @GetMapping(value = "/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Friend> getAllPendingFriends(@RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        return friendService.getAllPendingFriends(user.getId());
    }

    /**
     * Removes a Friend from the database
     * @param friend_id The id of the friend they wish to remove
     * @param token The Current User
     */
    @CrossOrigin
    @DeleteMapping(value = "/{friend_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void deleteFriend(@PathVariable("friend_id") String friend_id, @RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        friendService.deleteFriend(user.getId(), friend_id);
    }

    /**
     * Catches any exceptions in other methods and returns status code 401 if
     * a UnauthorizedException occurs.
     * @param e The unauthorized exception being thrown
     * @return A map containing the status code, error message, and timestamp of
     * when the error occurred.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Map<String, Object> handleUnauthorizedException(UnauthorizedException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 401);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return responseBody;
    }
    /**
     * Catches any exceptions in other methods and returns status code 403 if
     * a AuthenticationException occurs.
     * @param e The authentication exception being thrown
     * @return A map containing the status code, error message, and timestamp of
     * when the error occurred.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody Map<String, Object> handleAuthenticationException(AuthenticationException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 403);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return responseBody;
    }
    /**
     * Catches any exceptions in other methods and returns status code 404 if
     * a InvalidRequestException occurs.
     * @param e The invalid request exception being thrown
     * @return A map containing the status code, error message, and timestamp of
     * when the error occurred.
     */
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
}
