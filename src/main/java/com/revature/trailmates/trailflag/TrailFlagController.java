package com.revature.trailmates.trailflag;


import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.trailflag.dtos.requests.GetAllByDateIntAndUserIdRequest;
import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
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
@RequestMapping("/flag")
public class TrailFlagController {

    @Inject
    @Autowired
    private TrailFlagService trailFlagService;
    @Autowired
    private TokenService tokenService;

    public TrailFlagController() {super();}

    /**
     * gets all flags that match a dateInt and user ID
     * @param d The dateInt of the date to be queried
     * @param u the user ID to be queried
     * @return A list of TrailFlag objects
     */
    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,params = {"d","u"})
    public @ResponseBody Optional<List<TrailFlag>> getByDateIntAndUserId(@RequestParam Long d, String u, @RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        return trailFlagService.getAllByDateIntAndUserId(new GetAllByDateIntAndUserIdRequest(d,u));
    }
    /**
     * gets all flags that match a user ID
     * @param u the user ID to be queried
     * @return A list of TrailFlag objects
     */
    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,params = {"u"})
    public @ResponseBody Optional<List<TrailFlag>> getByUserId(@RequestParam String u, @RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        return trailFlagService.getAllByUserId(u);
    }
    /**
     * gets all flags that match a trail ID
     * @param t The trail ID to be queried
     * @return A list of TrailFlag objects
     */
    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,params = {"t"})
    public @ResponseBody Optional<List<TrailFlag>> getByTrailId(@RequestParam String t, @RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        return trailFlagService.getAllByTrailId(t);
    }
    /**
     * saves a trail flag to the database
     * @param request the request body, in JSON, with parameters trail_id, user_id, and date_int
     * @return On success, returns the TrailFlag that was saved
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TrailFlag saveNewTrailFlag(@RequestBody NewTrailFlagRequest request, @RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        return trailFlagService.saveNewTrailFlag(request);
    }
    /**
     * saves a trail flag to the database
     * @param id id of the TrailFlag to be deleted
     * @return On success, returns true
     */
    @CrossOrigin
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, params ={"id"})
    public @ResponseBody boolean deleteEntry(@RequestParam String id, @RequestHeader("Authorization") String token){
        Principal user = tokenService.noTokenThrow(token);
        if (trailFlagService.deleteTrailFlag(id)){
            return true;
        } else return false;
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
