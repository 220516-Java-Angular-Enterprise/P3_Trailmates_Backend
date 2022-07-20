package com.revature.trailmates.trailhistory;

import com.revature.trailmates.friends.Friend;
import com.revature.trailmates.friends.FriendService;
import com.revature.trailmates.notifications.NotificationService;
import com.revature.trailmates.notifications.dto.NewNotificationRequest;
import com.revature.trailmates.trailhistory.dto.requests.NewHistoryRequest;
import com.revature.trailmates.trailhistory.dto.response.History;
import com.revature.trailmates.user.User;
import com.revature.trailmates.user.UserService;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrailHistoryService {

    @Inject
    private final TrailHistoryRepository repo;
    private final FriendService friendService;
    private final NotificationService notificationService;
    private final UserService userService;

    @Autowired
    public TrailHistoryService(TrailHistoryRepository repo, FriendService friendService, NotificationService notificationService, UserService userService) {
        this.repo = repo;
        this.friendService = friendService;
        this.notificationService = notificationService;
        this.userService = userService;
    }



    public List<History> getAscHistory(String userID){
        return repo.getAscHistory(userID).stream().map(e -> new History().extractTrail(e)).collect(Collectors.toList());
    }

    public List<History> getDescHistory(String userID){
        return repo.getDescHistory(userID).stream().map(e -> new History().extractTrail(e)).collect(Collectors.toList());
    }

    public TrailHistory getHistory(String id) {
        return repo.getHistory(id);
    }

    public void insertNewHistory(NewHistoryRequest newHistory, String userID){
        Timestamp date = Timestamp.valueOf(newHistory.getDate().replaceAll("[A-Z]", " "));
        String trailID = repo.trailID(newHistory.getTrail_name());
        if(!correctDate(date)) throw new InvalidRequestException("Invalid Date");
        if(trailID == null) throw new InvalidRequestException("Trail doesn't exist");
        repo.addNewHistory(UUID.randomUUID().toString(), newHistory.getComment(), date, trailID, userID, newHistory.getImageURL());
        String id = UUID.randomUUID().toString();
        //Sending Notification to everyone that is friends with this user.
        User user = userService.getUserById(userID);
        List<Friend> friends = friendService.getAllFriendsFromFriendID(user.getId());
        for ( Friend f : friends ) {
            NewNotificationRequest request1 = new NewNotificationRequest();
            request1.setNotification_type("HISTORY");
            //String[] date = String.valueOf(request.getDateInt() * (1000 * 60 * 60 * 24)).split("\\s+");
            request1.setMessage(user.getUsername() + " has created a new post.");
            request1.setTarget_id(id);
            notificationService.addNotification(request1, f.getUser_id().getId());
        }
    }

    private boolean correctDate(Timestamp date){
        return date.getTime() <= System.currentTimeMillis();
    }


    /*private boolean isDuplicateHistory(Timestamp date, String trail_id){
        return repo.isDuplicateHistory(date, trail_id) != null;
    }*/

}
