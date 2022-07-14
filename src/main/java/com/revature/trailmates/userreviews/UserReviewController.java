package com.revature.trailmates.userreviews;

import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.trailflag.TrailFlag;
import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
import com.revature.trailmates.userreviews.dtos.requests.NewUserReviewRequest;
import com.revature.trailmates.userreviews.dtos.responses.ReviewSummaryResponse;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.AuthenticationException;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import com.revature.trailmates.util.custom_exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reviews/users")
public class UserReviewController {
    @Inject
    @Autowired
    private UserReviewService userReviewService;
    @Autowired
    private TokenService tokenService;

    /**
     *gets all user reviews attached to an authenticated user's id
     * @param token the authentication token provided under the Authorization header
     * @return a ReviewSummaryResponse object, which contains an average of reviews as its first element, and a list of anonymized reviews as its second element.
     */
    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ReviewSummaryResponse getByReviewer(@RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        //uses principal to request only the authenticated user's reviews.
        return userReviewService.getAllByReviewerId(user.getId());
    }

    /**
     * gets all reviews submitted on a particular user.  Results are anonymized, with only comment and rating visible.
     * @param token the authentication token provided under the Authorization header
     * @param u the id of the user whose reviews
     * @return a ReviewSummaryResponse object, which contains an average of reviews as its first element, and a list of anonymized reviews as its second element.
     */
    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,params = {"u"})
    public @ResponseBody ReviewSummaryResponse getByUser(@RequestHeader("Authorization") String token, @RequestParam String u) {
        Principal user = tokenService.noTokenThrow(token);
        return userReviewService.getAllByUserId(u);
    }
    //save new review (also has update functionality)

    /**
     * save a user review to the database
     * @param request the request body in JSON, which must contain 3 fields: a String userId, an integer rating 1-5, comment
     * @param token the authentication token provided under the Authorization header, and the source if the reviewer id
     * @return the user review that was saved
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody UserReview saveNewUserReview(@RequestHeader("Authorization") String token, @RequestBody NewUserReviewRequest request) {
        Principal user = tokenService.noTokenThrow(token);
        //technically the request could contain a reviewer ID, but the next line ensures that the authenticated user ID is the reviewer ID.
        request.setReviewerId(user.getId());
        return userReviewService.saveNewUserReview(request, user);
    }

    /**
     * delete the review of a user that belongs to the authenticated user's id
     * @param u the id of the user whose review is being deleted
     * @param token the authentication token provided under the Authorization header, and the source if the reviewer id
     * @return
     */
    @CrossOrigin
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, params ={"u"})
    public @ResponseBody String deleteReview( @RequestHeader("Authorization") String token, @RequestParam String u){
        Principal user = tokenService.noTokenThrow(token);
        if(userReviewService.deleteReview(u,user)){
            return "Review was deleted.";
        } else return "Failed to delete review.";
    }
    //region Exception Handlers
    /**
     * Catches any exceptions in other methods and returns status code 401 if
     * a UnauthorizedException occurs.
     * @param e The unauthorized exception being thrown
     * @return A map containing the status code, error message, and timestamp of
     * when the error occurred.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Map<String, Object> handleUnauthorizedException(UnauthorizedException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 401);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return responseBody;
    }
    /**
     * Catches any exceptions in other methods and returns status code 403 if
     * a AuthenticationException occurs.
     * @param e The authentication exception being thrown
     * @return A map containing the status code, error message, and timestamp of
     * when the error occurred.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody Map<String, Object> handleAuthenticationException(AuthenticationException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 403);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return responseBody;
    }
    /**
     * Catches any exceptions in other methods and returns status code 404 if
     * a InvalidRequestException occurs.
     * @param e The invalid request exception being thrown
     * @return A map containing the status code, error message, and timestamp of
     * when the error occurred.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Map<String, Object> handleInvalidRequestException(InvalidRequestException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 404);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        System.out.println(e.getMessage());
        return responseBody;
    }

    /**
     * Catches any exceptions in other methods and returns status code 409 if
     * a ResourceConflictException occurs.
     * @param e The resource conflict request being thrown
     * @return A map containing the status code, error message, and timestamp of
     * when the error occurred.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody Map<String, Object> handleResourceConflictException(ResourceConflictException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 409);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return responseBody;
    }
    //endregion
}
