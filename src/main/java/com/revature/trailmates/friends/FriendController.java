package com.revature.trailmates.friends;

import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

    @Inject
    private final FriendService friendService;

    @Autowired
    private TokenService tokenService;

    @Inject
    @Autowired
    public FriendController(FriendService friendService, TokenService tokenService) {
        this.friendService = friendService;
        this.tokenService = tokenService;
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

}
