package com.revature.trailmates.notifications;

import com.revature.trailmates.friends.FriendRepository;
import com.revature.trailmates.friends.FriendService;
import com.revature.trailmates.notifications.dto.NewNotificationRequest;
import com.revature.trailmates.trailhistory.TrailHistoryService;
import com.revature.trailmates.user.UserService;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class NotificationService {

    @Inject
    private final NotificationRepository notificationRepository;
    private final FriendRepository friendRepository;
    private final TrailHistoryService trailHistoryService;
    private final UserService userService;

    @Inject
    @Autowired
    public NotificationService(NotificationRepository notificationRepository, FriendRepository friendRepository, TrailHistoryService trailHistoryService, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.friendRepository = friendRepository;
        this.trailHistoryService = trailHistoryService;
        this.userService = userService;
    }

    public void addNotification(NewNotificationRequest request, String user_id) {
        Notification notification = new Notification();

        notification.setId(UUID.randomUUID().toString());
        notification.setUser_id(userService.getUserById(user_id));
        notification.setMessage(request.getMessage());
        notification.setNotification_type(request.getNotification_type());

        if (request.getNotification_type().equals("FRIEND")) {
            notification.setFriend(friendRepository.getFriend(request.getTarget_id(), user_id));
        }
        else if (request.getNotification_type().equals("HISTORY")) {
            notification.setTrailHistory(trailHistoryService.getHistory(request.getTarget_id()));
        }
        notificationRepository.save(notification);
    }

    public List<Notification> getAllNotificationsFromUser(String user_id) {
        return notificationRepository.getAllNotificationsFromUser(user_id);
    }
    public Optional<Notification> getNotification(String id) {
        return notificationRepository.findById(id);
    }
    public void deleteNotification(String id) {
        notificationRepository.deleteById(id);
    }
}

