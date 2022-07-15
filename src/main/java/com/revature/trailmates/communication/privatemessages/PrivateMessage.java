package com.revature.trailmates.communication.privatemessages;


import com.revature.trailmates.communication.conversation.Conversation;
import com.revature.trailmates.user.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "private_messages")
public class PrivateMessage {
    @Id
    private String id;
    @Column (name="message", nullable = false)
    private String message;
    @Column (name="time_sent", nullable = false)
    private Timestamp time_sent;
    @ManyToOne
    @JoinColumn(name="sender_id", nullable = false)
    private User sender_id;
    @ManyToOne
    @JoinColumn(name="conversation", nullable = false)
    private Conversation conversation;

    //region get/set

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

    public Timestamp getTime_sent() {
        return time_sent;
    }

    public void setTime_sent(Timestamp time_sent) {
        this.time_sent = time_sent;
    }

    public User getSender_id() {
        return sender_id;
    }

    public void setSender_id(User sender_id) {
        this.sender_id = sender_id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    //endregion

}
