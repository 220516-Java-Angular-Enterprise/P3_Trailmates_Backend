package com.revature.trailmates.friends;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FriendID implements Serializable {

    @Column(name = "user_id", nullable = false)
    private String user_id;
    @Column(name = "friend_id", nullable = false)
    private String friend_id;

    public FriendID() { }

    public FriendID(String user_id, String friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }
}
