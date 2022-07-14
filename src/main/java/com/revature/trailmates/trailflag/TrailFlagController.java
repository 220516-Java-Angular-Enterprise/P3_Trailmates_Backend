package com.revature.trailmates.trailflag;


import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
import com.revature.trailmates.util.annotations.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
     * gets all flags that match a dateInt and trail ID
     * @param d The dateInt of the date to be queried, added in the url as a parameter
     * @param t The trail ID to be queried, added in the url as a parameter
     * @param token the authentication token provided under the Authorization header
     * @return A list of TrailFlag objects
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,params = {"d","t"})
    public @ResponseBody Optional<List<TrailFlag>> getAllByDateIntAndTrailId(@RequestHeader("Authorization") String token,@RequestParam Long d, String t) {
        Principal user = tokenService.noTokenThrow(token);
        return trailFlagService.getAllByDateIntAndTrailId(d, t);
    }
    /**
     * gets all flags that match a dateInt and user ID
     * @param d The dateInt of the date to be queried, added in the url as a parameter
     * @param u the user ID to be queried, added in the url as a parameter
     * @param token the authentication token provided under the Authorization header
     * @return A list of TrailFlag objects
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,params = {"d","u"})
    public @ResponseBody Optional<List<TrailFlag>> getAllByDateIntAndUserId(@RequestHeader("Authorization") String token, @RequestParam Long d, String u) {
        Principal user = tokenService.noTokenThrow(token);
        return trailFlagService.getAllByDateIntAndUserId(d,u);
    }
    /**
     * gets all flags that match a user ID and trail ID
     * @param u The dateInt of the date to be queried, added in the url as a parameter
     * @param t The trail ID to be queried, added in the url as a parameter
     * @param token the authentication token provided under the Authorization header
     * @return A list of TrailFlag objects
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,params = {"u","t"})
    public @ResponseBody Optional<List<TrailFlag>> getAllByUserIdAndTrailId(@RequestHeader("Authorization") String token,@RequestParam String u, String t) {
        Principal user = tokenService.noTokenThrow(token);
        return trailFlagService.getAllByUserIdAndTrailId(u,t);
    }


    /**
     * gets all flags that match a user ID
     * @param u the user ID to be queried, added in the url as a parameter
     * @param token the authentication token provided under the Authorization header
     * @return A list of TrailFlag objects
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,params = {"u"})
    public @ResponseBody Optional<List<TrailFlag>> getAllByUserId(@RequestHeader("Authorization") String token, @RequestParam String u) {
        Principal user = tokenService.noTokenThrow(token);
        return trailFlagService.getAllByUserId(u);
    }
    /**
     * gets all flags that match a trail ID
     * @param t The trail ID to be queried, added in the url as a parameter
     * @param token the authentication token provided under the Authorization header
     * @return A list of TrailFlag objects
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,params = {"t"})
    public @ResponseBody Optional<List<TrailFlag>> getAllByTrailId(@RequestHeader("Authorization") String token, @RequestParam String t) {
        Principal user = tokenService.noTokenThrow(token);
        return trailFlagService.getAllByTrailId(t);
    }
    /**
     * saves a trail flag to the database
     * @param request the request body, in JSON, with parameters trail_id, user_id, and date_int
     * @param token the authentication token provided under the Authorization header
     * @return On success, returns the TrailFlag that was saved
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody TrailFlag saveNewTrailFlag(@RequestHeader("Authorization") String token, @RequestBody NewTrailFlagRequest request) {
        Principal user = tokenService.noTokenThrow(token);
        return trailFlagService.saveNewTrailFlag(request, user);
    }
    /**
     * deletes a trail flag from the database
     * @param id id of the TrailFlag to be deleted, added in the url as a parameter
     * @param token the authentication token provided under the Authorization header
     * @return On success, returns true
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, params ={"id"})
    public @ResponseBody String deleteEntry( @RequestHeader("Authorization") String token, @RequestParam String id){
        Principal user = tokenService.noTokenThrow(token);
        if(trailFlagService.deleteTrailFlag(id, user)){
            return "Flag was deleted.";
        } else return "Failed to delete flag.";
    }
}
