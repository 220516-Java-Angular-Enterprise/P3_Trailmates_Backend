package com.revature.trailmates.group.dto.requests;

public class NewGroupRequest {

    private String name;

    public NewGroupRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "NewGroupRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
