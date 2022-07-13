package com.revature.trailmates.notifications;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.revature.trailmates.friends.Friend;
import com.revature.trailmates.trailhistory.TrailHistory;
import com.revature.trailmates.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "notifications")
public class Notification {

    @Id
    private String id;

    @Column(name = "message")
    private String message;

    @Column(name = "timeCreated")
    private Timestamp timeCreated;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user_id;

    /* Different Notification Types:
            - FRIEND:   Someone added you as a friend.
            - HISTORY:  Lets you know someone made a comment
            - MESSAGE:  When someone sends you a message
            - GROUP:    (prob don't need) When you get add to a group ig.
    */
    @Column(name = "notification_type", nullable = false)
    private String notification_type;

    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "message_id", referencedColumnName = "id")
    //private Message message_id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user1_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "friend_id", referencedColumnName = "friend_id")})
    private Friend friend;

    @ManyToOne
    @JoinColumn(name = "trail_history_id", referencedColumnName = "id")
    private TrailHistory trailHistory;

    public Notification(String id, String message, Timestamp timeCreated, User user_id, String notification_type, Friend friend, TrailHistory trailHistory) {
        this.id = id;
        this.message = message;
        this.timeCreated = timeCreated;
        this.user_id = user_id;
        this.notification_type = notification_type;
        this.friend = friend;
        this.trailHistory = trailHistory;
    }

    public Timestamp getTimeCreated() { return timeCreated; }
    public void setTimeCreated(Timestamp timeCreated) { this.timeCreated = timeCreated; }

    public Notification() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public TrailHistory getTrailHistory() {
        return trailHistory;
    }

    public void setTrailHistory(TrailHistory trailHistory) {
        this.trailHistory = trailHistory;
    }
}
