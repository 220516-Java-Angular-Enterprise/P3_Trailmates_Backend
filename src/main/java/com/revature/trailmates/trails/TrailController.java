package com.revature.trailmates.trails;

import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
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
    private final TokenService tokenService;

    @Inject
    @Autowired
    public TrailController(TrailService trailService, TokenService tokenService) {
        this.trailService = trailService;
        this.tokenService = tokenService;
    }



    /**
     * Returns a Trail Object with a matching id
     * @param id the id of the trail
     * @return Returns a Trail Object
     */
    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Optional<Trail> getTrailById(@PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        return trailService.getTrail(id);
    }

    /**
     * Returns All Trails on a given Page (10 Trails Per Page)
     * @param page
     * @return A List of at most 10 trails at a certain page
     */
//    @CrossOrigin
//    @GetMapping(value = "/getAll/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody List<Trail> getAllTrailsByPage(@PathVariable int page, @RequestHeader("Authorization") String token) {
//        Principal user = tokenService.noTokenThrow(token);
//        return trailService.getAllTrailsPage(page);
//    }

    /**
     * Returns a List of Every Single Trail in the Database
     * @return List<Trail>
     */
    @CrossOrigin
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trail> getAllTrails(@RequestHeader("Authorization") String token) {
        Principal user = tokenService.noTokenThrow(token);
        return trailService.getAllTrails();
    }

    /**
     * Returns a List of Trails the fit the search criteria of search_name on a specific page
     * @param search_name The value the user types into our search bar
     * @param page The page that they want to go to.
     * @return List<Trail> A list of at most 10 trails
     */
//    @CrossOrigin
//    @GetMapping(value = "search/{page}/{search_name}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody List<Trail> searchTrailByName(@PathVariable("search_name") String search_name, @PathVariable("page") int page, @RequestHeader("Authorization") String token) {
//        Principal user = tokenService.noTokenThrow(token);
//        return trailService.searchTrailByName(search_name, page);
//    }

    /**
     * Returns a List of Trails that fit the State search criteria on a specified page
     * @param state The state value user is inserting
     * @param page The page the user is on
     * @return List<Trail> A List of 10 trails or less if on the last page
     */
//    @CrossOrigin
//    @GetMapping(value = "searchState/{page}/{state}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody List<Trail> searchTrailByState(@PathVariable("state") String state, @PathVariable("page") int page, @RequestHeader("Authorization") String token) {
//        Principal user = tokenService.noTokenThrow(token);
//        return trailService.searchTrailByState(state, page);
//    }

    /**
     * Returns a List of Trails that fir the Park Name search criteria on a specific page
     * @param search_park
     * @param page
     * @return
     */
//    @CrossOrigin
//    @GetMapping(value = "searchPark/{page}/{search_park}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody List<Trail> searchTrailByParkName(@PathVariable("search_park") String search_park, @PathVariable("page") int page, @RequestHeader("Authorization") String token) {
//        Principal user = tokenService.noTokenThrow(token);
//        return trailService.searchTrailByParkName(search_park, page);
//    }


    //FOR BACKEND USE ONLY, DON'T CALL THIS ON FRONT END.
    //@CrossOrigin
    //@GetMapping(value = "/getAllAPI/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    //public @ResponseBody List<Trail> getAllTrailsAPI(@PathVariable int page) { return trailService.getAllTrailsAPI(page); }


}
