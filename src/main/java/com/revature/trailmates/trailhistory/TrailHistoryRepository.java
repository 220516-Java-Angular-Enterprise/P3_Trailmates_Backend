package com.revature.trailmates.trailhistory;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Repository
public interface TrailHistoryRepository extends CrudRepository<TrailHistory, String> {

    @Query(value = "select * from trailhistory where user_id = ?1 order by trail_date ASC", nativeQuery = true)
    List<TrailHistory> getAscHistory(String userID);

    @Query(value = "select * from trailhistory where user_id = ?1 order by trail_date DESC", nativeQuery = true)
    List<TrailHistory> getDescHistory(String userID);

    @Modifying
    @Query(value = "insert into trailhistory values(?1,?2,?3,?4,?5, ?6)", nativeQuery = true)
    void addNewHistory(String id, String comment, Timestamp date, String trail_id, String user_id, String image_url);

    @Query(value = "select id from trails where name = ?1", nativeQuery = true)
    String trailID(String trail_name);

    @Query(value = "SELECT FROM trails WHERE id = ?1", nativeQuery = true)
    TrailHistory getHistory(String id);

    /*@Query(value = "select id from trailhistory where trail_date = ?1 and trail_id = ?2")
    String isDuplicateHistory(Timestamp date, String trail_id);*/
}
