package com.revature.trailmates.notifications;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface NotificationRepository extends CrudRepository<Notification, String> {

    //@Modifying
    //@Query(value = "INSERT INTO notifications (id, message, notification_type, user_id, friend_id, user1_id, trail_history_id)", nativeQuery = true)
    //void addNotification(String id, String message, String user_id, String friend_id, String user1_id, String trail_history_id);

    @Query(value = "SELECT * FROM notifications WHERE user_id = ?1", nativeQuery = true)
    List<Notification> getAllNotificationsFromUser(String user_id);

}
