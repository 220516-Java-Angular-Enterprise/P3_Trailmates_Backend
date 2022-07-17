package com.revature.trailmates.friends;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.revature.trailmates.notifications.Notification;
import com.revature.trailmates.user.User;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "friends")
public class Friend {

    @EmbeddedId
    private FriendID friendID;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user_id;

    @ManyToOne
    @JoinColumn(name = "friend_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User friend_id;

    public Friend() { }

    public FriendID getFriendID() {
        return friendID;
    }

    public void setFriendID(FriendID friendID) {
        this.friendID = friendID;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public User getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(User friend_id) {
        this.friend_id = friend_id;
    }
}
