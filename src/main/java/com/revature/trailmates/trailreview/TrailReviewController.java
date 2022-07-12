package com.revature.trailmates.trailreview;

import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.requests.NewUserRequest;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.trailreview.dtos.requests.TrailReviewRequest;
import com.revature.trailmates.trailreview.dtos.responses.TrailAverageRating;
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

@RestController
@RequestMapping("/trailreview")
public class TrailReviewController {

    @Inject
    private TrailReviewService trailReviewService;
    private TokenService tokenService;

    @Inject
    @Autowired
    public TrailReviewController(TrailReviewService trailReviewService, TokenService tokenService) {
        this.trailReviewService = trailReviewService;
        this.tokenService = tokenService;
    }

    /**
     * Creates a new review for a trail based on the trailID and tokens userID
     * @param token The token of the given user trying to call the backend
     * @param trailReviewRequest The new trail review being created, contains a comment (0-255 characters) and rating (1-5 BigDecimal)
     * @param trailID The trail the review is being put on
     * @see TrailReviewRequest
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{trailID}",consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createReview(@RequestHeader("Authorization") String token, @RequestBody TrailReviewRequest trailReviewRequest, @PathVariable String trailID){
        Principal requester = tokenService.noTokenThrow(token);
        trailReviewService.newTrailReview(trailReviewRequest, requester.getId(), trailID);
    }

    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping(value = "/{trailID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteTrailReview(@RequestHeader("Authorization") String token, @PathVariable String trailID){
        Principal requester = tokenService.noTokenThrow(token);
        trailReviewService.deleteReview(requester.getId(),trailID);
    }

    /**
     * Gets the Average rating and Count of all the reviews on a given trail
     * @param token The token of the given user trying to call the backend
     * @param trailID The trailID to get the trail
     * @return returns a TrailAverageRating object containing the trailID the ratingAvg (x.xx BigDecimal) and ratingCount (Integer)
     * @see TrailAverageRating
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping(value = "/avg/{trailID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TrailAverageRating getAverageReviewsForATrail(@RequestHeader("Authorization") String token, @PathVariable String trailID){
        Principal requester = tokenService.noTokenThrow(token);
        return trailReviewService.getAverageReviewsForTrail(trailID);
    }


    /**
     * Gets a list of TrailReviews for a specific trail based on the given trailID
     * @param token The token of the given user trying to call the backend
     * @param trailID The trailID to get the trail
     * @return returns a list of TrailReviews for the trail
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping(value = "/all/{trailID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrailReview> getAllReviewsForATrail(@RequestHeader("Authorization") String token, @PathVariable String trailID){
        Principal requester = tokenService.noTokenThrow(token);
        return trailReviewService.getAllReviewsForTrail(trailID);
    }



    //region Exception Handlers
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Map<String, Object> handleUnauthorizedException(UnauthorizedException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 401);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return responseBody;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody Map<String, Object> handleAuthenticationException(AuthenticationException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 403);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
        return responseBody;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Map<String, Object> handleInvalidRequestException(InvalidRequestException e){
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 404);
        responseBody.put("message", e.getMessage());
        responseBody.put("timestamp", LocalDateTime.now().toString());
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
