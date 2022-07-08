package com.revature.trailmates.trails;

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
@RequestMapping("/trail")
public class TrailController {

    @Inject
    private final TrailService trailService;

    @Inject
    @Autowired
    public TrailController(TrailService trailService) {
        this.trailService = trailService;
    }

    /**
     * Returns a Trail Object with a matching id
     * @param id the id of the trail
     * @return Returns a Trail Object
     */
    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Optional<Trail> getTrailById(@PathVariable String id) { return trailService.getTrail(id); }

    /**
     * Returns All Trails on a given Page (10 Trails Per Page)
     * @param page
     * @return A List of at most 10 trails at a certain page
     */
    @CrossOrigin
    @GetMapping(value = "/getAll/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trail> getAllTrailsByPage(@PathVariable int page) { return trailService.getAllTrailsPage(page); }

    /**
     * Returns a List of Every Single Trail in the Database
     * @return List<Trail>
     */
    @CrossOrigin
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trail> getAllTrails() { return trailService.getAllTrails(); }

    /**
     * Returns a List of Trails the fit the search criteria of search_name on a specific page
     * @param search_name The value the user types into our search bar
     * @param page The page that they want to go to.
     * @return List<Trail> A list of at most 10 trails
     */
    @CrossOrigin
    @GetMapping(value = "search/{page}/{search_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trail> searchTrailByName(@PathVariable("search_name") String search_name, @PathVariable("page") int page) { return trailService.searchTrailByName(search_name, page); }

    /**
     * Returns a List of Trails that fit the State search criteria on a specified page
     * @param state The state value user is inserting
     * @param page The page the user is on
     * @return List<Trail> A List of 10 trails or less if on the last page
     */
    @CrossOrigin
    @GetMapping(value = "searchState/{page}/{state}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trail> searchTrailByState(@PathVariable("state") String state, @PathVariable("page") int page) { return trailService.searchTrailByState(state, page); }

    /**
     * Returns a List of Trails that fir the Park Name search criteria on a specific page
     * @param search_park
     * @param page
     * @return
     */
    @CrossOrigin
    @GetMapping(value = "searchPark/{page}/{search_park}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trail> searchTrailByParkName(@PathVariable("search_park") String search_park, @PathVariable("page") int page) { return trailService.searchTrailByParkName(search_park, page); }


    //FOR BACKEND USE ONLY, DON'T CALL THIS ON FRONT END.
    //@CrossOrigin
    //@GetMapping(value = "/getAllAPI/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    //public @ResponseBody List<Trail> getAllTrailsAPI(@PathVariable int page) { return trailService.getAllTrailsAPI(page); }


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

}
