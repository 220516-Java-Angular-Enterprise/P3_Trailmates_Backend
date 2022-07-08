package com.revature.trailmates.trailreview;

import com.revature.trailmates.trailreview.dtos.requests.TrailReviewRequest;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TrailReviewService {

    @Inject
    private TrailReviewRepository trailReviewRepository;

    @Inject
    @Autowired
    public TrailReviewService(TrailReviewRepository trailReviewRepository) {
        this.trailReviewRepository = trailReviewRepository;
    }

    //region Public methods
    public void newTrailReview(TrailReviewRequest trailReviewRequest, String userID, String trailID){
        TrailReview review= trailReviewRequest.createTrailReview(userID, trailID);
        if(!trailExists(trailID)) throw new InvalidRequestException("Cannot find trail");
        if(!userExists(userID)) throw new InvalidRequestException("Cannot find user");
        if(review.getComment().length() > 255) throw new InvalidRequestException("Comment is longer than 255 characters");
        if(review.getRating().compareTo(BigDecimal.valueOf(5)) <= 0) throw new InvalidRequestException("Rating is greater than 5 stars");
        if(review.getRating().compareTo(BigDecimal.valueOf(0)) >= 0) throw new InvalidRequestException("Rating is less than 0 stars");
        //validation checks here
        if(checkIfTrailReviewExists(review)) {
            updateTrailReview(review);
            return;
        }
        trailReviewRepository.save(review);
    }

    public void updateTrailReview(TrailReview trailReview){
        trailReviewRepository.updateTrailReview(trailReview.getComment(),
                trailReview.getRating(),
                trailReview.getTrailReviewID().getUserID(),
                trailReview.getTrailReviewID().getTrailID());
    }

    public void deleteReview(TrailReview trailReview){
        trailReviewRepository.delete(trailReview);
    }

    public List<TrailReview> getAllReviewsForTrail(String trailID){
        List<TrailReview> trailReviews = new ArrayList<>();
        trailReviewRepository.findAll().forEach(trailReviews::add);
        if(trailReviews.isEmpty()) throw new InvalidRequestException("No reviews found");
        return trailReviews;
    }

    public TrailReview getReviewByTrailIDAndUserID(String trailID, String userID){
        TrailReview review = trailReviewRepository.ifReviewExists(trailID, userID);
        if(review == null) throw new InvalidRequestException("Cannot find trail review");
        return review;
    }
    //endregion

    //region Validation Checks
    private boolean checkIfTrailReviewExists(TrailReview trailReview){
        return trailReviewRepository.ifReviewExists(trailReview.getTrailReviewID().getUserID(), trailReview.getTrailReviewID().getTrailID()) != null;
    }

    private boolean trailExists(String trailID){
        return true;
    }

    private boolean userExists(String userID){
        return true;
    }
    //endregion


}
