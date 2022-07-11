package com.revature.trailmates.trailreview;

import com.revature.trailmates.trails.Trail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TrailReviewRepository extends CrudRepository<TrailReview, String> {

    @Query(value = "SELECT * FROM trail_review WHERE user_id = ?1 AND trail_id = ?2", nativeQuery = true)
    TrailReview ifReviewExists(String userID, String trailID);

    @Modifying
    @Query(value = "UPDATE trail_review SET comment = ?1 AND rating = ?2 WHERE user_id = ?3 AND trail_id = ?4", nativeQuery = true)
    void updateTrailReview(String comment, BigDecimal rating, String userID, String trailID);
}
