package com.revature.trailmates.friends;

import com.revature.trailmates.user.User;
import com.revature.trailmates.user.UserRepository;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class FriendServiceTest {

    @Mock
    private FriendRepository friendRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FriendService friendService;

    @Spy
    Friend dummyFriend = new Friend();

    @Test
    void addNewFriend() {
        assertThrows(InvalidRequestException.class, () -> friendService.addNewFriend(null, null));
    }

    @Test
    void getAllFriendsFromUser() {
        assertThrows(InvalidRequestException.class, () -> friendService.getAllFriendsFromUser(null));
    }

    @Test
    void getAllPendingFriends() {
        assertThrows(InvalidRequestException.class, () -> friendService.getAllPendingFriends(null));
    }

    @Test
    void deleteFriend() {
        assertThrows(InvalidRequestException.class, () -> friendService.deleteFriend(null, null));
    }
}