package com.revature.trailmates.trailflag.dtos.requests;


public class GetAllByDateIntAndTrailIdRequest {


private String trail_id;

    private long date_int;


    public GetAllByDateIntAndTrailIdRequest() {
    }

    public GetAllByDateIntAndTrailIdRequest(long date_int, String user_id) {
        this.trail_id = user_id;
        this.date_int = date_int;
    }

    public String getTrail_id() {
        return trail_id;
    }

    public void setTrail_id(String user_id) {
        this.trail_id = user_id;
    }

    public long getDate_int() {
        return date_int;
    }

    public void setDate_int(long date_int) {
        this.date_int = date_int;
    }
}
