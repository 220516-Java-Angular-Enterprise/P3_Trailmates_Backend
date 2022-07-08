package com.revature.trailmates.communication.privatemessages.dto.requests;

public class NewPrivateMessageRequest {

    //private String id;
    private String message;
    private long time_sent;
    private String sender_id;
    private String conversation_id;

    public NewPrivateMessageRequest(){}

    //region get/set

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime_sent() {
        return time_sent;
    }

    public void setTime_sent(long time_sent) {
        this.time_sent = time_sent;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
    }

    //endregion


}
