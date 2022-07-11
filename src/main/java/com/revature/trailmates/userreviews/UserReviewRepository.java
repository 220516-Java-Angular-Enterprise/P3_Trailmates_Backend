package com.revature.trailmates.userreviews;

import com.revature.trailmates.trailflag.TrailFlag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserReviewRepository extends CrudRepository<UserReview, String> {






    //get all reviews by user
    @Query(value ="SELECT * FROM user_reviews where user_id = ?1", nativeQuery =true)
    Optional<List<UserReview>> getAllByUserId(String user_id);
    //get all reviews by reviewer
    @Query(value ="SELECT * FROM user_reviews where reviewer_id = ?1", nativeQuery =true)
    Optional<List<UserReview>> getAllByReviewerId(String reviewer_id);
    //select by composite id
    @Query(value ="SELECT * FROM user_reviews where user_id = ?1 and reviewer_id = ?2", nativeQuery =true)
    Optional<List<UserReview>> getByCompositeId(String user_id, String reviewer_id);
    //delete review
    @Query(value ="DELETE * FROM user_reviews where user_id = ?1 and reviewer_id = ?2", nativeQuery =true)
    Optional<List<UserReview>> deleteByCompositeId(String user_id, String reviewer_id);
    //edit review
    @Query(value ="UPDATE  user_reviews SET rating=?3, comment=?4  where user_id = ?1 and reviewer_id = ?2", nativeQuery =true)
    Optional<List<UserReview>> updateReview(String user_id, String reviewer_id, int rating, String comment);
}
