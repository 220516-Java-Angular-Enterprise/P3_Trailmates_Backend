package com.revature.trailmates.trailhistory;


import com.revature.trailmates.friends.Friend;
import com.revature.trailmates.friends.FriendService;
import com.revature.trailmates.imagedata.ImageData;
import com.revature.trailmates.notifications.NotificationService;
import com.revature.trailmates.trailhistory.dto.requests.NewHistoryRequest;
import com.revature.trailmates.trailhistory.dto.response.History;
import com.revature.trailmates.trails.Trail;
import com.revature.trailmates.user.User;
import com.revature.trailmates.user.UserService;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TrailHistoryServiceTest {

    @Mock
    private TrailHistoryRepository repo;

    @Mock
    private UserService userService;

    @Mock
    private FriendService friendService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TrailHistoryService service;

    @Spy
    NewHistoryRequest newHistory;

    @Spy
    History history;

    @Spy
    TrailHistory trail;

    @Test
    void getAscHistory() {
        List<TrailHistory> trailHistoryList = new ArrayList<>();
        trail = new TrailHistory("123", "213", new Timestamp(System.currentTimeMillis()), new User(), new Trail(), new ImageData());
        trailHistoryList.add(trail);
        when(repo.getAscHistory(any(String.class))).thenReturn(trailHistoryList);
        List<History> historyList = new ArrayList<>();
        historyList.add(new History().extractTrail(trailHistoryList.get(0)));
        assertEquals(historyList.toString(), service.getAscHistory("sad").toString());
    }

    @Test
    void getDescHistory() {
        List<TrailHistory> trailHistoryList = new ArrayList<>();
        trail = new TrailHistory("123", "213", new Timestamp(System.currentTimeMillis()), new User(), new Trail(), new ImageData());
        trailHistoryList.add(trail);
        when(repo.getDescHistory(any(String.class))).thenReturn(trailHistoryList);
        List<History> historyList = new ArrayList<>();
        historyList.add(new History().extractTrail(trailHistoryList.get(0)));
        assertEquals(historyList.toString(), service.getDescHistory("sad").toString());
    }

    @Test
    void insertWrongTrailName() {
        newHistory.setComment("demo");
        newHistory.setTrail_name("asd");
        newHistory.setDate("2022-01-10 13:45:00.0");
        assertThrows(InvalidRequestException.class, () -> service.insertNewHistory(newHistory, "0c2b4bc1-7270-4264-96c0-7d897fbd1771"));
    }

    @Test
    void insertWrongDate() {
        newHistory.setComment("demo");
        newHistory.setTrail_name("asd");
        newHistory.setDate("2022-12-10 13:45:00.0");
        assertThrows(InvalidRequestException.class, () -> service.insertNewHistory(newHistory, "0c2b4bc1-7270-4264-96c0-7d897fbd1771"));
    }

    @Test
    void addNewHistory(){
        List<TrailHistory> trailHistoryList = new ArrayList<>();
        Trail trails = new Trail();
        trails.setName("1234");
        trail.setTrail(trails);
        trailHistoryList.add(trail);

        User dummyUser = new User();
        dummyUser.setId("yes");

        List<Friend> dummyList = new ArrayList<>();
        dummyList.add(new Friend());
        dummyList.get(0).setUser_id(dummyUser);

        //when(repo.getAscHistory(any(String.class))).thenReturn(trailHistoryList);
        doAnswer(invocationOnMock -> null).when(repo).addNewHistory(any(String.class), any(String.class),any(Timestamp.class),any(String.class), any(String.class), any(String.class));
        Mockito.when(repo.trailID(any())).thenReturn("12");
        Mockito.when(userService.getUserById(any())).thenReturn(dummyUser);
        Mockito.when(friendService.getAllFriendsFromFriendID(any())).thenReturn(dummyList);
        repo.addNewHistory("name", "123", new Timestamp(System.currentTimeMillis()), "12", "12345", "123546");
        newHistory.setDate("2021-09-12 12:12:00.00");
        newHistory.setTrail_name("1234");
        newHistory.setComment("123");
        newHistory.setImageURL("123454");
        service.insertNewHistory(newHistory, "123456");
        Mockito.verify(notificationService, times(1)).addNotification(any(),any());
    }



}