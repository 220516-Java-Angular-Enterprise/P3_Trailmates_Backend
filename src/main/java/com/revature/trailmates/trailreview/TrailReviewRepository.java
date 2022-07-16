package com.revature.trailmates.trailreview;

import com.revature.trailmates.trailreview.dtos.responses.TrailAverageRating;
import com.revature.trailmates.trails.Trail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TrailReviewRepository extends CrudRepository<TrailReview, String> {

    @Query(value = "SELECT * FROM trail_review WHERE trail_id = ?1 AND user_id = ?2", nativeQuery = true)
    TrailReview ifReviewExists(String trailID, String userID);

    @Modifying
    @Query(value = "UPDATE trail_review SET comment = ?1, rating = ?2 WHERE user_id = ?3 AND trail_id = ?4", nativeQuery = true)
    void updateTrailReview(String comment, BigDecimal rating, String userID, String trailID);

    @Query(value = "SELECT avg(rating) AS ratingAvg, count(rating) AS ratingCount FROM trail_review GROUP BY trail_id HAVING trail_id = ?1", nativeQuery = true)
    TrailReviewAverage avgRating(String trailID);


    @Query(value = "SELECT * FROM trail_review WHERE trail_id = ?1", nativeQuery = true)
    List<TrailReview> getAllReviewsOfATrail(String trailID);

    @Modifying
    @Query(value = "DELETE FROM trail_review WHERE user_id = ?1 AND trail_id = ?2", nativeQuery = true)
    void deleteTrailReview(String userID, String trailID);
}
