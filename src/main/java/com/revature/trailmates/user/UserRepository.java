package com.revature.trailmates.user;


//This is the class that talks to the database.


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    //<editor-fold desc="Query">

    @Query (value = "SELECT * FROM users WHERE id = ?1", nativeQuery = true)
    User getUserByID(String id);
    @Query (value = "SELECT * FROM users WHERE username = ?1", nativeQuery = true)
    User getUserByUsername(String username);
    //</editor-fold desc="Query>

    //<editor-fold desc="Save">
    @Modifying
    @Query (value = "INSERT INTO users (id, username, password, email, role, bio, age) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
    public void saveUser(User user);
    //</editor-fold desc="Save">

    //<editor-fold desc="Update User">
    @Modifying
    @Query (value = "UPDATE users SET email = ?1, bio = ?2, age = ?3 WHERE id = ?4", nativeQuery = true)
    void updateUser(String email, String bio, int age, String id);
    //</editor-fold desc="Update User">

}
