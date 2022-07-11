package com.revature.trailmates.userreviews;

import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.trailflag.TrailFlag;
import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
import com.revature.trailmates.userreviews.dtos.requests.NewUserReviewRequest;
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
    //get all by reviewer id
    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Optional<List<UserReview>> getByReviewer(@RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        //uses principal to request only the authenticated user's reviews.
        return userReviewService.getAllByReviewerId(user.getId());
    }
    //get all by user id
    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,params = {"u"})
    public @ResponseBody Optional<List<UserReview>> getByUser(@RequestParam String u, @RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        return userReviewService.getAllByUserId(u);
    }
    //save new review (also has update functionality)
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody UserReview saveNewUserReview(@RequestBody NewUserReviewRequest request, @RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        return userReviewService.saveNewUserReview(request, user);
    }
    //delete review
    @CrossOrigin
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, params ={"u"})
    public @ResponseBody String deleteReview(@RequestParam String u, @RequestHeader("Authorization") String token){
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
