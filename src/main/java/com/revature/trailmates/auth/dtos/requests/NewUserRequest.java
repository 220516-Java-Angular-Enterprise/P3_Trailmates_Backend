package com.revature.trailmates.auth.dtos.requests;

import com.revature.trailmates.user.User;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.UUID;

public class NewUserRequest {

    private String username;
    private String password;
    private final String role = "DEFAULT";
    private String email;

    private String bio;

    private int age;

    public NewUserRequest() {
        super();
    }


    public NewUserRequest(String username, String password, String email, String bio, int age) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User extractUser(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(getUsername());
        user.setPassword(getPassword());
        user.setEmail(getEmail());
        user.setBio(getBio());
        user.setAge(getAge());
        //user.setActive(false);
        user.setRole("DEFAULT");
        return user;
    }
}
