package com.revature.trailmates.notifications;

import com.revature.trailmates.communication.ownedconversation.OwnedConversation;
import com.revature.trailmates.communication.ownedconversation.OwnedConversationRepository;
import com.revature.trailmates.friends.Friend;
import com.revature.trailmates.friends.FriendRepository;
import com.revature.trailmates.friends.FriendService;
import com.revature.trailmates.notifications.dto.NewNotificationRequest;
import com.revature.trailmates.trailflag.TrailFlag;
import com.revature.trailmates.trailhistory.TrailHistory;
import com.revature.trailmates.trailhistory.TrailHistoryService;
import com.revature.trailmates.trails.Trail;
import com.revature.trailmates.trails.TrailService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private FriendRepository friendRepository;
    @Mock
    private TrailHistoryService trailHistoryService;
    @Mock
    private TrailService trailService;
    @Mock
    private OwnedConversationRepository ownedConversationRepository;
    @Mock
    private UserService userService;


    @InjectMocks
    private NotificationService notificationService;


    @Test
    void addNotificationFriend() {
        NewNotificationRequest request = new NewNotificationRequest();
        request.setNotification_type("FRIEND");
        request.setTarget_id("1");
        request.setMessage("test");

        Friend dummyFriend = new Friend();
        User dummyUser = new User();

        Mockito.when(friendRepository.getFriend(request.getTarget_id(),"0")).thenReturn(dummyFriend);
        Mockito.when(userService.getUserById("0")).thenReturn(dummyUser);

        notificationService.addNotification(request, "0");
        verify(notificationRepository, Mockito.times(1)).save(any());
    }

//    @Test
//    void addNotificationHistory() {
//        NewNotificationRequest request = new NewNotificationRequest();
//        request.setNotification_type("HISTORY");
//        request.setTarget_id("1");
//        request.setMessage("test");
//
//        TrailHistory dummyHistory = new TrailHistory();
//        User dummyUser = new User();
//
//        Mockito.when(trailHistoryService.getHistory(request.getTarget_id())).thenReturn(dummyHistory);
//        Mockito.when(userService.getUserById("0")).thenReturn(dummyUser);
//
//        notificationService.addNotification(request, "0");
//        verify(notificationRepository, Mockito.times(1)).save(any());
//    }

    @Test
    void addNotificationFlag() {
        NewNotificationRequest request = new NewNotificationRequest();
        request.setNotification_type("FLAG");
        request.setTarget_id("1");
        request.setMessage("test");

        Trail dummyTrail = new Trail();
        User dummyUser = new User();

        Mockito.when(trailService.getTrailByID("1")).thenReturn(dummyTrail);
        Mockito.when(userService.getUserById("0")).thenReturn(dummyUser);

        notificationService.addNotification(request, "0");
        verify(notificationRepository, Mockito.times(1)).save(any());
    }

    @Test
    void addNotificationConvo() {
        NewNotificationRequest request = new NewNotificationRequest();
        request.setNotification_type("NEW_CONVO");
        request.setTarget_id("1");
        request.setMessage("test");

        OwnedConversation dummyConvo = new OwnedConversation();
        User dummyUser = new User();

        Mockito.when(ownedConversationRepository.getOwnedConversationByID("1")).thenReturn(dummyConvo);
        Mockito.when(userService.getUserById("0")).thenReturn(dummyUser);

        notificationService.addNotification(request, "0");
        verify(notificationRepository, Mockito.times(1)).save(any());
    }

    @Test
    void getAllNotificationsFromUser() {
        assertThrows(InvalidRequestException.class, () -> notificationService.getAllNotificationsFromUser(null));
    }

    @Test
    void getNotification() {
        assertThrows(InvalidRequestException.class, () -> notificationService.getNotification(null));
    }

    @Test
    void deleteNotification() {
        assertThrows(InvalidRequestException.class, () -> notificationService.deleteNotification(null));
    }
}