package com.revature.trailmates.trailhistory;

import com.revature.trailmates.trailhistory.dto.requests.NewHistory;
import com.revature.trailmates.trailhistory.dto.response.History;
import com.revature.trailmates.trails.Trail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TrailHistoryRepository extends CrudRepository<TrailHistory, String> {
/*
    @Query(value = "select * from trailhistory where user_id = ?1 order by trailDate ASC", nativeQuery = true)
    List<History> getAscHistory(String userID);

    @Query(value = "select * from trailhistory where user_id = ?1 order by trailDate DESC", nativeQuery = true)
    List<History> getDescHistory(String userID);*/

    @Query(value = "select NEW History(name as trailname, username as partnerName, th.comment, trail_date) from trailhistory as th\n" +
            "inner join trails as t on th.trail_id = t.id \n" +
            "inner join users as u on th.user_id = u.id \n" +
            "where user_id = ?1 order by trail_date ASC", nativeQuery = true)
    List<History> getAscHistory(String userID);

    @Query(value = "select * from trailhistory where user_id = ?1 order by trail_date DESC", nativeQuery = true)
    List<TrailHistory> getDescHistory(String userID);

    @Modifying
    @Query(value = "insert into trailhistory values(?1,?2,?3,?4,?5)", nativeQuery = true)
    void addNewHistory(String id, String comment, Timestamp date, String trail_id, String user_id);
}
