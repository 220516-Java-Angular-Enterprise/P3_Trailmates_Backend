package com.revature.trailmates.trailflag;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TrailFlagRepository extends CrudRepository<TrailFlag,String> {

    @Query(value ="SELECT * FROM trail_flags where date_int = ?1 and user_id = ?2", nativeQuery =true)
    Optional<List<TrailFlag>> getAllByDateIntAndUserId(long dateInt, String user_id);
    @Query(value ="SELECT * FROM trail_flags where user_id = ?1", nativeQuery =true)
    Optional<List<TrailFlag>> getAllByUserId(String user_id);
    @Query(value ="SELECT * FROM trail_flags where trail_id = ?1", nativeQuery =true)
    Optional<List<TrailFlag>> getAllByTrailId(String trail_id);
    @Query(value ="SELECT * FROM trail_flags where date_int = ?1 and user_id = ?2 and trail_id = ?3", nativeQuery =true)
    Optional<List<TrailFlag>> getAllByDateIntAndUserIdAndTrailId(long dateInt, String user_id, String trail_id);
    @Query(value ="SELECT * FROM trail_flags where date_int = ?1 and trail_id = ?2", nativeQuery =true)
    Optional<List<TrailFlag>> getAllByDateIntAndTrailId(long dateInt, String trail_id);
}
