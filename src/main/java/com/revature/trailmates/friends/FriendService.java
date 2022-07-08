package com.revature.trailmates.friends;

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

    @Inject
    @Autowired
    public FriendService(FriendRepository friendRepository, UserService userService) {
        this.friendRepository = friendRepository;
        this.userService = userService;
    }

    public void addNewFriend(String user_id, String friend_id) {
        if (user_id == null || friend_id == null) throw new InvalidRequestException("User ID or Friend ID is null");
        friendRepository.addNewFriend(user_id, friend_id);
    }

    public List<Friend> getAllFriendsFromUser(String user_id) {
        if (user_id == null) throw new InvalidRequestException("User ID is null.");
        return friendRepository.getAllFriendsFromUser(user_id);
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
                result.add(new Friend(new FriendID(friend.getFriendID().getFriend_id(), friend.getFriendID().getUser_id())));
            }
        }
        return result;
    }

    public void deleteFriend(String user_id, String friend_id) {
        if (user_id == null || friend_id == null) throw new InvalidRequestException("User ID or Friend ID is null");
        friendRepository.deleteFriend(user_id, friend_id);
    }
}
