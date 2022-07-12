package com.revature.trailmates.trailreview;

import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.trailreview.dtos.requests.TrailReviewRequest;
import com.revature.trailmates.trailreview.dtos.responses.TrailAverageRating;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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

    /**
     * @param token
     * @param trailID
     */
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

}
