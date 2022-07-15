package com.revature.trailmates.imagedata.dtos.requests;

public class NewImageDataRequest {
    private String url;
    private String filetype;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
}
