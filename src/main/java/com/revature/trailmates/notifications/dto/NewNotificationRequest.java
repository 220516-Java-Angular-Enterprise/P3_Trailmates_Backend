package com.revature.trailmates.notifications.dto;

public class NewNotificationRequest {

    private String message;
    private String notification_type;
    private String target_id;

    public NewNotificationRequest(String message, String notification_type, String target_id) {
        this.message = message;
        this.notification_type = notification_type;
        this.target_id = target_id;
    }

    public NewNotificationRequest() { }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }

    @Override
    public String toString() {
        return "NewNotificationRequest{" +
                "message='" + message + '\'' +
                ", notification_type='" + notification_type + '\'' +
                ", target_id='" + target_id + '\'' +
                '}';
    }
}
