package com.revature.trailmates.trailreview;

import com.revature.trailmates.trailreview.dtos.requests.TrailReviewRequest;
import com.revature.trailmates.trailreview.dtos.responses.TrailAverageRating;
import com.revature.trailmates.trails.TrailService;
import com.revature.trailmates.user.UserService;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TrailReviewService {

    @Inject
    private TrailReviewRepository trailReviewRepository;
    private TrailService trailService;
    private UserService userService;

    @Inject
    @Autowired
    public TrailReviewService(TrailReviewRepository trailReviewRepository, TrailService trailService, UserService userService) {
        this.trailReviewRepository = trailReviewRepository;
        this.trailService = trailService;
        this.userService = userService;
    }

    //region Public methods
    public void newTrailReview(TrailReviewRequest trailReviewRequest, String userID, String trailID){
        TrailReview review= trailReviewRequest.createTrailReview(userID, trailID);
        if(!trailExists(trailID)) throw new InvalidRequestException("Cannot find trail");
        if(!userExists(userID)) throw new InvalidRequestException("Cannot find user");
        if(review.getComment().length() > 255) throw new InvalidRequestException("Comment is longer than 255 characters");
        if(review.getRating().compareTo(BigDecimal.valueOf(5)) > 0) throw new InvalidRequestException("Rating is greater than 5 stars");
        if(review.getRating().compareTo(BigDecimal.valueOf(1)) < 0) throw new InvalidRequestException("Rating is less than 1 stars");
        //validation checks here
        if(checkIfTrailReviewExists(userID, trailID)) {
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

    public void deleteReview(String userID, String trailID){
        if(!checkIfTrailReviewExists(userID, trailID)) throw new InvalidRequestException("Trail Review does not exist");
        trailReviewRepository.deleteTrailReview(userID, trailID);
    }

    public TrailAverageRating getAverageReviewsForTrail(String trailID){
        if(!trailExists(trailID)) throw new InvalidRequestException("Cannot find trail");
        TrailReviewAverage trailReviewAverage = trailReviewRepository.avgRating(trailID);
        return new TrailAverageRating(trailID, trailReviewAverage.getRatingAvg().setScale(2, RoundingMode.HALF_UP), trailReviewAverage.getRatingCount());
    }
    public List<TrailReview> getAllReviewsForTrail(String trailID){
        if(!trailExists(trailID)) throw new InvalidRequestException("Cannot find trail");
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
    private boolean checkIfTrailReviewExists(String userID, String trailID){
        return trailReviewRepository.ifReviewExists(userID, trailID) != null;
    }

    private boolean trailExists(String trailID){
        try {
            trailService.getTrail(trailID);
            return true;
        }catch (InvalidRequestException exception){
            return false;
        }
    }

    private boolean userExists(String userID){
        return userService.getUserById(userID) != null;
    }
    //endregion


}
