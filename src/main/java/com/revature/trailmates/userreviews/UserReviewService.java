package com.revature.trailmates.userreviews;

import com.revature.trailmates.userreviews.dtos.requests.NewUserReviewRequest;
import com.revature.trailmates.userreviews.dtos.responses.ReviewSummaryResponse;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.AuthenticationException;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import com.revature.trailmates.auth.dtos.response.Principal;
@Service
@Transactional
public class UserReviewService {
    @Inject
    private final UserReviewRepository userReviewRepository;
    @Autowired
    public UserReviewService (UserReviewRepository userReviewRepository){
        this.userReviewRepository=userReviewRepository;
    }
    //get all reviews by user id
    public ReviewSummaryResponse getAllByUserId(String user_id){
        Optional<List<UserReview>> retrievedList = userReviewRepository.getAllByUserId(user_id);
        if (!retrievedList.isPresent()||retrievedList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any reviews for that user.");
        } else {
            OptionalDouble revAvg = retrievedList.get().stream().mapToDouble(r -> r.getRating()).average();
            return new ReviewSummaryResponse(revAvg.orElse(0.0),retrievedList.get());
        }
    }
    //get all reviews by reviewer id.
    public ReviewSummaryResponse getAllByReviewerId(String reviewer_id){
        Optional<List<UserReview>> retrievedList = userReviewRepository.getAllByReviewerId(reviewer_id);
        if (!retrievedList.isPresent()||retrievedList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any reviews by that reviewer.");
        } else {
                OptionalDouble revAvg = retrievedList.get().stream().mapToDouble(r -> r.getRating()).average();
            return new ReviewSummaryResponse(revAvg.orElse(0.0),retrievedList.get());
        }
    }
    //save new review
    public UserReview saveNewUserReview(NewUserReviewRequest request, Principal requestor) {
        //if the new review request has null values other than the comment, throw exception
        String nullCheck = nullChecker(request);
        if (!nullCheck.isEmpty()) {
            if (nullCheck.contains("compId")||nullCheck.contains("rating")) {
                throw new InvalidRequestException(nullChecker(request));
            }
        }
        //check that the review rating is 1-5 stars
        if (request.getRating()<1||request.getRating()>5){
            throw new InvalidRequestException("The rating in the review must be an integer in the range 1-5.");
        }
        //check that requestor is not same as target of review
        if (requestor.getId().equals(request.getUserId())){
            throw new InvalidRequestException("Cannot submit review for ID that matches ID user making request");
        }
        UserReview newReview = new UserReview(request);
        //if review already exists, update instead of save
        if (this.isDuplicateReview(newReview)) {
            try{
                this.editReview(newReview);
            } catch (InvalidRequestException e){throw e;}
            return newReview;
        }
        //finally, save new review if we didn't end up just editing an old one.
        try {
            userReviewRepository.save(newReview);
        } catch (Exception e) {
            throw new InvalidRequestException("Unable to save review.  Either the user or reviewer id were not found in the database.");
        }
        return newReview;
    }
    //method is private because edit function is rolled into the save function.
    private void editReview(UserReview review){
        try {
            userReviewRepository.updateReview(review.getRating(),review.getComment(),review.getUserId().getId(),review.getReviewerId().getId());
        } catch (Exception e) {
            throw new InvalidRequestException("Unable to update review.");
        }
    }
    //delete user review
    public boolean deleteReview(String user_id, Principal requestor){
        //deletes id using requestor ID as reviewer ID
        try {
            userReviewRepository.deleteByCompositeId(user_id,requestor.getId());
            return true;
        } catch (Exception e){
            throw new InvalidRequestException("Failed to delete user review.");
        }
    }
    private String nullChecker(NewUserReviewRequest request){
        String eMessage = "";
        //Checks if any fields are null and builds a message accordingly
        try {
            Field[] fields = com.revature.trailmates.userreviews.dtos.requests.NewUserReviewRequest.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(request) == null) {
                    if(!eMessage.isEmpty()){
                        eMessage += ", ";
                    }
                    eMessage += field.getName() + " is null";
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return eMessage;
    }
    private boolean isDuplicateReview(UserReview review){
        Optional<List<UserReview>> returnList = userReviewRepository.getByCompositeId(review.getUserId().getId(), review.getReviewerId().getId());
        if (!returnList.isPresent()||returnList.get().size()==0){
            return false;
        } else {
            return true;
        }
    }
}
