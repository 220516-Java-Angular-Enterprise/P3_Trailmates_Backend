package com.revature.trailmates.friends;

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "friends")
public class Friend {

    @EmbeddedId
    private FriendID friendID;

    public Friend(FriendID friendID) {
        this.friendID = friendID;
    }

    public Friend() { }

    public FriendID getFriendID() {
        return friendID;
    }

    public void setFriendID(FriendID friendID) {
        this.friendID = friendID;
    }

}
