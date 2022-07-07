package com.revature.trailmates.trailhistory;


import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.trailhistory.dto.requests.NewHistory;
import com.revature.trailmates.trailhistory.dto.response.History;
import com.revature.trailmates.trails.Trail;
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
     * @return returns a list of history sorted in descending order
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CrossOrigin
    @GetMapping(path = "/desc", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<TrailHistory> descendingTrailHistory(@RequestHeader("Authorization") String token){
        Principal user = tokenService.noTokenThrow(token);
        return trailHistoryService.getDescHistory(user.getId());
    }

    /**
     * @return returns a list of history sorted in ascending order
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CrossOrigin
    @GetMapping(path = "/asc", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<TrailHistory> ascendingTrailHistory(@RequestHeader("Authorization") String token){
        Principal user = tokenService.noTokenThrow(token);
        return trailHistoryService.getAscHistory(user.getId());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @CrossOrigin
    @PostMapping(path = "/newHistory", consumes = "application/json",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    NewHistory insertingNewHistory(@RequestHeader("Authorization") String token, @RequestBody NewHistory newHistory){
        Principal user = tokenService.noTokenThrow(token);
        trailHistoryService.insertNewHistory(newHistory, user.getId());
        return newHistory;
    }


    //region Exception Handlers
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    Map<String, Object> handleUnauthorizedException(UnauthorizedException e){
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
