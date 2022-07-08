package com.revature.trailmates.auth.dtos.response;


import com.revature.trailmates.user.User;

public class Principal {

    private String token;
    private String id;
    private String username;
    private String role;

    public Principal() {
        super();
    }

    public Principal(User user) {
        this.token = null;
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }

    public Principal(String id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
