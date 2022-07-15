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
     * @param t The trail ID to be queried, added in the url as a parameter
     * @param d a long with the date as an int that represents days in UNIX epoch
     * @return A list of TrailFlag objects
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/dateAndTrail/{d}/{t}",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Optional<List<TrailFlag>> getAllByDateIntAndTrailId(@RequestHeader("Authorization") String token,@PathVariable Long d, @PathVariable String t) {
        Principal user = tokenService.noTokenThrow(token);
        return trailFlagService.getAllByDateIntAndTrailId(d, t);
    }
    /**
     * gets all flags that match a dateInt and user ID
     * @param u the user ID to be queried, added in the url as a parameter
     * @param d a long with the date as an int that represents days in UNIX epoch
     * @return A list of TrailFlag objects
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/dateAndUser/{d}/{u}",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Optional<List<TrailFlag>> getAllByDateIntAndUserId(@RequestHeader("Authorization") String token, @PathVariable long d, @PathVariable String u) {
        Principal user = tokenService.noTokenThrow(token);
        return trailFlagService.getAllByDateIntAndUserId(d,u);
    }
    /**
     * gets all flags that match a user ID and trail ID
     * @param u the user ID to be queried, added in the url as a parameter
     * @param t The trail ID to be queried, added in the url as a parameter
     * @return A list of TrailFlag objects
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/userAndTrail/{u}/{t}",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Optional<List<TrailFlag>> getAllByUserIdAndTrailId(@RequestHeader("Authorization") String token,@PathVariable String u, @PathVariable String t) {
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
    @GetMapping(value="/user/{u}",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Optional<List<TrailFlag>> getAllByUserId(@RequestHeader("Authorization") String token, @PathVariable String u) {
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
    @GetMapping(value="/trail/{t}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Optional<List<TrailFlag>> getAllByTrailId(@RequestHeader("Authorization") String token, @PathVariable String t) {
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
     * @return On success, returns true
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value="/delete/{id}",produces = MediaType.APPLICATION_JSON_VALUE, params ={"id"})
    public @ResponseBody String deleteEntry( @RequestHeader("Authorization") String token, @PathVariable String id){
        Principal user = tokenService.noTokenThrow(token);
        if(trailFlagService.deleteTrailFlag(id, user)){
            return "Flag was deleted.";
        } else return "Failed to delete flag.";
    }
}
