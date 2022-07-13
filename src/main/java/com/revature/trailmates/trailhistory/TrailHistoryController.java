package com.revature.trailmates.trailhistory;


import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.trailhistory.dto.requests.NewHistoryRequest;
import com.revature.trailmates.trailhistory.dto.response.History;
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
@RequestMapping("/history")
public class TrailHistoryController {

    @Inject
    @Autowired
    private TrailHistoryService trailHistoryService;

    @Autowired
    private TokenService tokenService;

    public TrailHistoryController() {
        super();
    }

    /**
     * @param token verifying it is a user in the database
     * @return returns a list of history sorted in descending order
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CrossOrigin
    @GetMapping(path = "/desc", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<History> descendingTrailHistory(@RequestHeader("Authorization") String token){
        Principal user = tokenService.noTokenThrow(token);
        return trailHistoryService.getDescHistory(user.getId());
    }

    /**
     * @param token verifying it is a user in the database
     * @return returns a list of history sorted in ascending order
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CrossOrigin
    @GetMapping(path = "/asc", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<History> ascendingTrailHistory(@RequestHeader("Authorization") String token){
        Principal user = tokenService.noTokenThrow(token);
        return trailHistoryService.getAscHistory(user.getId());
    }

    /**
     * @param token authorized user
     * @param userID specific user we want to retrieve from
     * @return the list of their trail history
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CrossOrigin
    @GetMapping(path = "/asc/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<History> userTrailHistory(@RequestHeader("Authorization") String token, @PathVariable String userID){
        Principal user = tokenService.noTokenThrow(token);
        return trailHistoryService.getAscHistory(userID);
    }


    /**
     * @param token verifying it is a user in the database
     * @param newHistory taking in a json object to create a new history object
     * @return returning the json object used
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CrossOrigin
    @PostMapping(path = "/newHistory", consumes = "application/json",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    NewHistoryRequest insertingNewHistory(@RequestHeader("Authorization") String token, @RequestBody NewHistoryRequest newHistory){
        Principal user = tokenService.noTokenThrow(token);
        trailHistoryService.insertNewHistory(newHistory, user.getId());
        return newHistory;
    }

}
