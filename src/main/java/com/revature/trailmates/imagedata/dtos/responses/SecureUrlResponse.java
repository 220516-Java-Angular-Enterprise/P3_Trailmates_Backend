package com.revature.trailmates.imagedata.dtos.responses;

public class SecureUrlResponse {
    private String url;

    public SecureUrlResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SecureUrlResponse{" +
                "url='" + url + '\'' +
                '}';
    }
}
