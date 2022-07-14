package com.revature.trailmates.communication.privatemessages.dto.requests;

import java.sql.Timestamp;

public class NewPrivateMessageRequest {

    //private String id;
    private String message;
    private Timestamp time_sent;
    private String conversation_id;

    public NewPrivateMessageRequest(){}

    //region get/set

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

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    //endregion


}
