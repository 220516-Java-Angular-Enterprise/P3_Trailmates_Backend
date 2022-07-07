package com.revature.trailmates.trails;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trails")
public class Trail {

    @Id
    private String id;
    @Column(name="name")
    private String name;
    @Column(name="park_name")
    private String park_name;
    @Column(name="short_desc", columnDefinition = "TEXT")
    private String short_desc;
    @Column(name="long_desc", columnDefinition = "TEXT")
    private String long_desc;
    @Column(name="image_url")
    private String image_url;
    @Column(name="website_url")
    private String website_url;
    @Column(name="is_reservation_required")
    private Boolean isReservationRequired;
    @Column(name="are_pets_permitted")
    private Boolean arePetsPermitted;
    @Column(name="do_fees_apply")
    private Boolean doFeesApply;
    @Column(name="duration")
    private String duration;
    @Column(name="states")
    private String states;
    @Column(name="park_code")
    private String parkCode;
    @Column(name="latitude")
    private String latitude;
    @Column(name="longitude")
    private String longitude;

    public Trail(String id, String name, String park_name, String short_desc, String long_desc, String image_url, String website_url, Boolean isReservationRequired, Boolean arePetsPermitted, Boolean doFeesApply, String duration, String states, String parkCode, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.park_name = park_name;
        this.short_desc = short_desc;
        this.long_desc = long_desc;
        this.image_url = image_url;
        this.website_url = website_url;
        this.isReservationRequired = isReservationRequired;
        this.arePetsPermitted = arePetsPermitted;
        this.doFeesApply = doFeesApply;
        this.duration = duration;
        this.states = states;
        this.parkCode = parkCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Trail() { }

    //<editor-fold desc="Get/Set">

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPark_name() {
        return park_name;
    }

    public void setPark_name(String park_name) {
        this.park_name = park_name;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getLong_desc() {
        return long_desc;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(String website_url) {
        this.website_url = website_url;
    }

    public Boolean getReservationRequired() {
        return isReservationRequired;
    }

    public void setReservationRequired(Boolean reservationRequired) {
        isReservationRequired = reservationRequired;
    }

    public Boolean getArePetsPermitted() {
        return arePetsPermitted;
    }

    public void setArePetsPermitted(Boolean arePetsPermitted) {
        this.arePetsPermitted = arePetsPermitted;
    }

    public Boolean getDoFeesApply() {
        return doFeesApply;
    }

    public void setDoFeesApply(Boolean doFeesApply) {
        this.doFeesApply = doFeesApply;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    //</editor-fold>

}
