package com.revature.trailmates.friends;

import com.revature.trailmates.notifications.NotificationService;
import com.revature.trailmates.notifications.dto.NewNotificationRequest;
import com.revature.trailmates.user.User;
import com.revature.trailmates.user.UserService;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FriendService {

    @Inject
    private final FriendRepository friendRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    @Inject
    @Autowired
    public FriendService(FriendRepository friendRepository, UserService userService, NotificationService notificationService) {
        this.friendRepository = friendRepository;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public void addNewFriend(String user_id, String friend_id) {
        if (user_id == null || friend_id == null) throw new InvalidRequestException("User ID or Friend ID is null");
        if (friendRepository.getFriend(user_id, friend_id) != null ) throw new InvalidRequestException("User is already friends with that friend_id");
        friendRepository.addNewFriend(user_id, friend_id);

        // Sends out a friend Notification to the friend_id
        NewNotificationRequest request = new NewNotificationRequest();
        User user = userService.getUserById(user_id);
        request.setMessage(user.getUsername() + " has added you as a friend.");
        request.setNotification_type("FRIEND");
        request.setTarget_id(user_id);
        notificationService.addNotification(request, friend_id);
    }

    public Friend getFriend(String user_id, String friend_id) {
        return friendRepository.getFriend(user_id, friend_id);
    }

    public List<Friend> getAllFriendsFromUser(String user_id) {
        if (user_id == null) throw new InvalidRequestException("User ID is null.");
        return friendRepository.getAllFriendsFromUser(user_id);
    }

    public List<Friend> getAllFriendsFromFriendID(String friend_id) {
        if (friend_id == null) throw new InvalidRequestException("User ID is null.");
        return friendRepository.getAllFriendsFromFriendID(friend_id);
    }

    public List<Friend> getAllPendingFriends(String friend_id) {
        if (friend_id == null) throw new InvalidRequestException("Friend_id is null.");
        List<Friend> list1 = friendRepository.getAllFriendsFromFriendID(friend_id);
        List<Friend> list2 = friendRepository.getAllFriendsFromUser(friend_id);
        List<Friend> result = new ArrayList<>();

        for ( Friend friend : list1 ) {
            notPending:
            {
                for (Friend friend2 : list2) {
                    if (friend2.getFriendID().getUser_id().equals(friend.getFriendID().getFriend_id()) && friend2.getFriendID().getFriend_id().equals(friend.getFriendID().getUser_id()))
                        break notPending;
                }
                Friend friend1 = new Friend();
                friend1.setFriendID(new FriendID(friend.getFriendID().getFriend_id(), friend.getFriendID().getUser_id()));
                friend1.setUser_id(userService.getUserById(friend1.getFriendID().getUser_id()));
                friend1.setFriend_id(userService.getUserById(friend1.getFriendID().getFriend_id()));
                result.add(friend1);
            }
        }
        return result;
    }

    public void deleteFriend(String user_id, String friend_id) {
        if (user_id == null || friend_id == null) throw new InvalidRequestException("User ID or Friend ID is null");
        friendRepository.deleteFriend(user_id, friend_id);
    }
}
