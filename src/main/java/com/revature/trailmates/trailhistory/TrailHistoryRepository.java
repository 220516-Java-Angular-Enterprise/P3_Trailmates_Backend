package com.revature.trailmates.trailhistory;

import com.revature.trailmates.trailhistory.dto.response.History;
import com.revature.trailmates.trails.Trail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TrailHistoryRepository extends CrudRepository<TrailHistory, String> {
/*
    @Query(value = "select * from trailhistory where user_id = ?1 order by trailDate ASC", nativeQuery = true)
    List<History> getAscHistory(String userID);

    @Query(value = "select * from trailhistory where user_id = ?1 order by trailDate DESC", nativeQuery = true)
    List<History> getDescHistory(String userID);*/

    @Query(value = "select * from trailhistory where user_id = ?1 order by trailDate ASC", nativeQuery = true)
    List<TrailHistory> getAscHistory(String userID);

    @Query(value = "select * from trailhistory where user_id = ?1 order by trailDate DESC", nativeQuery = true)
    List<TrailHistory> getDescHistory(String userID);
}
