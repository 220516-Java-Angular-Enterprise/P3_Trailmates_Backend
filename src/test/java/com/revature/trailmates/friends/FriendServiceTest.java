package com.revature.trailmates.friends;

import com.amazonaws.services.ec2.model.transform.UserBucketStaxUnmarshaller;
import com.revature.trailmates.notifications.Notification;
import com.revature.trailmates.notifications.NotificationService;
import com.revature.trailmates.notifications.dto.NewNotificationRequest;
import com.revature.trailmates.user.User;
import com.revature.trailmates.user.UserRepository;
import com.revature.trailmates.user.UserService;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FriendServiceTest {

    @Mock
    private FriendRepository friendRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private FriendService friendService;

    @Spy
    Friend dummyFriend = new Friend();


    @Test
    void addNewFriendWithNullValues() {
        assertThrows(InvalidRequestException.class, () -> friendService.addNewFriend(null, null));
    }

    @Test
    void friendAlreadyExists() {
        // Arrange
        String friend_id = "810f2743-0210-4569-9101-e8c84a0c1f41";
        String user_id = "5f702c57-1f86-441a-a37d-2fc6278d7560";

        Friend friend = Mockito.mock(Friend.class);
        Mockito.when(friendService.getFriend(user_id, friend_id)).thenReturn(friend);
        assertThrows(InvalidRequestException.class, () -> friendService.addNewFriend(user_id, friend_id));
    }

    @Test
    void addFriend() {
        String user_id = "5f702c57-1f86-441a-a37d-2fc6278d7560";
        String friend_id = "810f2743-0210-4569-9101-e8c84a0c1f41";

        User dummy1 = new User();
        Mockito.when(friendService.getFriend(user_id, friend_id)).thenReturn(null);
        Mockito.when(userService.getUserById(user_id)).thenReturn(dummy1);

        friendService.addNewFriend(user_id, friend_id);

        verify(friendRepository, Mockito.times(1)).addNewFriend(user_id, friend_id);
    }

    @Test
    void getAllPendingFriends() {
        String friend_id = "810f2743-0210-4569-9101-e8c84a0c1f41";
        FriendID dummyId = new FriendID("0", "1");
        Friend dummyFriend = new Friend();
        dummyFriend.setFriendID(dummyId);

        List<Friend> dummyList = new ArrayList<>();
        dummyList.add(dummyFriend);

        Mockito.when(friendRepository.getAllFriendsFromFriendID(friend_id)).thenReturn(dummyList);
        Mockito.when(friendRepository.getAllFriendsFromUser(friend_id)).thenReturn(dummyList);

        friendService.getAllPendingFriends(friend_id);

        List<Friend> resultList = friendService.getAllPendingFriends(friend_id);
        assertEquals(dummyList.get(0).getFriendID().getUser_id(), resultList.get(0).getFriendID().getFriend_id());
    }

    @Test
    void getAllPendingWithNullID() {
        assertThrows(InvalidRequestException.class, () -> friendService.getAllPendingFriends(null));
    }

    @Test
    void getAllFriendsFromUser() {
        assertThrows(InvalidRequestException.class, () -> friendService.getAllFriendsFromUser(null));

        String user_id = "810f2743-0210-4569-9101-e8c84a0c1f41";

        List<Friend> dummyList = new ArrayList<>();

        Mockito.when(friendRepository.getAllFriendsFromUser(user_id)).thenReturn(dummyList);

        List<Friend> resultList = friendService.getAllFriendsFromUser(user_id);
        assertEquals(dummyList, resultList);
    }

    @Test
    void getAllFriendsFromFriendID() {
        assertThrows(InvalidRequestException.class, () -> friendService.getAllFriendsFromFriendID(null));

        String friend_id = "810f2743-0210-4569-9101-e8c84a0c1f41";

        List<Friend> dummyList = new ArrayList<>();

        Mockito.when(friendRepository.getAllFriendsFromFriendID(friend_id)).thenReturn(dummyList);

        List<Friend> resultList = friendService.getAllFriendsFromFriendID(friend_id);
        assertEquals(dummyList, resultList);
    }

    @Test
    void deleteFriend() {
        assertThrows(InvalidRequestException.class, () -> friendService.deleteFriend(null, null));

        String user_id = "5f702c57-1f86-441a-a37d-2fc6278d7560";
        String friend_id = "810f2743-0210-4569-9101-e8c84a0c1f41";

        User dummy1 = new User();

        friendService.deleteFriend(user_id, friend_id);

        verify(friendRepository, Mockito.times(1)).deleteFriend(user_id, friend_id);
    }
}