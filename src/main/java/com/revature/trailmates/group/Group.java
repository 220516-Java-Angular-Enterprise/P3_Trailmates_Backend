package com.revature.trailmates.group;

import com.revature.trailmates.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group")
public class Group {

    @Id
    private String id;
    @Column(name ="gc_id", nullable = true)
    private String gcId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "size",nullable = false)
    private int size;

    @ManyToMany
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

    public Group() {
    }

    public Group(String id, String gc_id, String name, int size, List<User> users) {
        this.id = id;
        this.gcId = gc_id;
        this.name = name;
        this.size = size;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGc_id() {
        return gcId;
    }

    public void setGc_id(String gc_id) {
        this.gcId = gc_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", gc_id='" + gcId + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", users=" + users +
                '}';
    }
}
