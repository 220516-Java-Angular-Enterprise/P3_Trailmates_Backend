package com.revature.trailmates.friends;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface FriendRepository extends CrudRepository<Friend, String> {

    @Modifying
    @Query(value = "INSERT INTO friends (user_id, friend_id) VALUES (?1, ?2)", nativeQuery = true)
    void addNewFriend(String user_id, String friend_id);

    @Query(value = "SELECT * FROM friends WHERE user_id = ?1", nativeQuery = true)
    List<Friend> getAllFriendsFromUser(String user_id);

    @Query(value = "SELECT * FROM friends WHERE friend_id = ?1", nativeQuery = true)
    List<Friend> getAllFriendsFromFriendID(String friend_id);

    @Modifying
    @Query(value = "DELETE FROM friends WHERE user_id = ?1 AND friend_id = ?2", nativeQuery = true)
    void deleteFriend(String user_id, String friend_id);

    @Query(value = "SELECT * FROM friends WHERE user_id = ?1 AND friend_id = ?2", nativeQuery = true)
    Friend getFriend(String user_id, String friend_id);
}
