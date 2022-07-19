package com.revature.trailmates.trailflag;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TrailFlagRepository extends CrudRepository<TrailFlag,String> {

    @Query(value ="SELECT * FROM trail_flags where user_id = ?1", nativeQuery =true)
    Optional<List<TrailFlag>> getAllByUserId(String userId);
    @Query(value ="SELECT * FROM trail_flags where trail_id = ?1", nativeQuery =true)
    Optional<List<TrailFlag>> getAllByTrailId(String trailId);
    @Query(value ="SELECT * FROM trail_flags where user_id = ?1 and date-int >= ?2", nativeQuery =true)
    Optional<List<TrailFlag>> getAllActiveByUserId(String userId, long todayteInt);
    @Query(value ="SELECT * FROM trail_flags where trail_id = ?1 and date_int >= ?2", nativeQuery =true)
    Optional<List<TrailFlag>> getAllActiveByTrailId(String trailId, long todayteInt);
    @Query(value ="SELECT * FROM trail_flags where date_int = ?1 and user_id = ?2", nativeQuery =true)
    Optional<List<TrailFlag>> getAllByDateIntAndUserId(long dateInt, String userId);
    @Query(value ="SELECT * FROM trail_flags where date_int = ?1 and user_id = ?2 and trail_id = ?3", nativeQuery =true)
    Optional<List<TrailFlag>> getAllByDateIntAndUserIdAndTrailId(long dateInt, String userId, String trailId);
    @Query(value ="SELECT * FROM trail_flags where date_int = ?1 and trail_id = ?2", nativeQuery =true)
    Optional<List<TrailFlag>> getAllByDateIntAndTrailId(long dateInt, String trailId);
    @Query(value ="SELECT * FROM trail_flags where user_id = ?1 and trail_id = ?2", nativeQuery =true)
    Optional<List<TrailFlag>> getAllByUserIdAndTrailId(String userId, String trailId);
}
