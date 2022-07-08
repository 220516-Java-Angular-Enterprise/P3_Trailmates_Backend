package com.revature.trailmates.friends;

import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

    @Inject
    private final FriendService friendService;

    @Inject
    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    /**
     * Adds a Friend to the database
     * @param user_id The user who is adding the friend
     * @param friend_id The id of the friend the user is adding
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{user_id}/{friend_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void addFriend(@PathVariable("user_id") String user_id, @PathVariable("friend_id") String friend_id) {
        friendService.addNewFriend(user_id, friend_id);
    }

    /**
     * Returns a list of all the friends a user has
     * @param user_id id of the user
     * @return List of Friends
     */
    @CrossOrigin
    @GetMapping(value = "/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Friend> getAllFriendsFromUser(@PathVariable String user_id) {
        return friendService.getAllFriendsFromUser(user_id);
    }

    /**
     * Returns a List of all users who have current user as their friend but the current user doesn't have
     * as their friend.
     * @param friend_id Id of the current user
     * @return List of pending friends
     */
    @CrossOrigin
    @GetMapping(value = "/pending/{friend_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Friend> getAllPendingFriends(@PathVariable String friend_id) {
        return friendService.getAllPendingFriends(friend_id);
    }

    /**
     * Removes a Friend from the database
     * @param user_id The current users id
     * @param friend_id the id of the friend they wish to remove
     */
    @CrossOrigin
    @DeleteMapping(value = "/{user_id}/{friend_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void deleteFriend(@PathVariable("user_id") String user_id, @PathVariable("friend_id") String friend_id) {
        friendService.deleteFriend(user_id, friend_id);
    }
}
