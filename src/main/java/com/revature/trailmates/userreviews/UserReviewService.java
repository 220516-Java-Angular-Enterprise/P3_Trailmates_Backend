package com.revature.trailmates.userreviews;

import com.revature.trailmates.userreviews.dtos.requests.NewUserReviewRequest;
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
    public Optional<List<UserReview>> getAllByUserId(String user_id){
        Optional<List<UserReview>> returnList = userReviewRepository.getAllByUserId(user_id);
        if (!returnList.isPresent()||returnList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any reviews for that user.");
        } else return returnList;
    }
    //get all reviews by reviewer id.
    public Optional<List<UserReview>> getAllByReviewerId(String reviewer_id){
        Optional<List<UserReview>> returnList = userReviewRepository.getAllByReviewerId(reviewer_id);
        if (!returnList.isPresent()||returnList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any reviews by that reviewer.");
        } else return returnList;
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
        //check that requestor has same ID as reviewer_ID
        if (!requestor.getId().equals(request.getCompId().getReviewer_id())){
            throw new AuthenticationException("Reviewer ID does not match person submitting review.");
        }
        //check that requestor is not same as target of review
        if (requestor.getId().equals(request.getCompId().getUser_id().getId())){
            throw new InvalidRequestException("Cannot submit review for ID that matches ID user making request");
        }
        UserReview newReview = new UserReview(request);
        //if review already exists, update instead of save
        if (isDuplicateReview(newReview)) {
            try{
                this.editReview(newReview);
            } catch (InvalidRequestException e){throw e;}
            return newReview;
        }
        //finally, save new review
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
            userReviewRepository.updateReview(review.extractUser_id(),review.extractReviewer_id(), review.getRating(),review.getComment());
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
        Optional<List<UserReview>> returnList = userReviewRepository.getByCompositeId(review.extractUser_id(), review.extractReviewer_id());
        if (!returnList.isPresent()||returnList.get().size()==0){
            return false;
        } else return true;
    }
}
