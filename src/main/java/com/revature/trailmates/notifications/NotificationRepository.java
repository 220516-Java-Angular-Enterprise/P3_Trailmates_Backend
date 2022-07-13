package com.revature.trailmates.notifications;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface NotificationRepository extends CrudRepository<Notification, String> {
    @Query(value = "SELECT * FROM notifications WHERE user_id = ?1", nativeQuery = true)
    List<Notification> getAllNotificationsFromUser(String user_id);

}
