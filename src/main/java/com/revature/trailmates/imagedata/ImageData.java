package com.revature.trailmates.imagedata;

import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.imagedata.dtos.requests.NewImageDataRequest;
import com.revature.trailmates.user.User;

import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;
@Entity
@Table(name = "imagedata")
public class ImageData {
    @Id
    private String url;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable=false)
    private User userId;
//    //url is unnecessary, as the id is the url
//    @Column(name = "url")
//    private String URL;
    @Column(name = "timestamp")
    private Timestamp timestamp;
    @Column(name = "filetype")
    private String filetype;


    public ImageData() {super();}

    public ImageData(NewImageDataRequest request, Principal user, Date date) {
        this.url = request.getUrl();
        this.filetype = request.getFiletype();
        this.timestamp = new Timestamp(date.getTime());
        this.userId=new User();
        userId.setId(user.getId());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }



}
