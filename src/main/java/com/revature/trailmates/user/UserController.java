package com.revature.trailmates.user;


//This is the class that wraps servlets for all netcode


import com.revature.trailmates.auth.AuthService;
import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.user.dtos.requests.EditUserRequest;
import com.revature.trailmates.util.annotations.*;
import com.revature.trailmates.util.custom_exception.AuthenticationException;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import com.revature.trailmates.util.custom_exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Inject
    private final UserService userService;
    @Inject
    private final TokenService tokenService;

    @Inject
    @Autowired
    public UserController(UserService userService, TokenService tokenService){
        this.userService = userService;
        this.tokenService = tokenService;
    }
    //region getting users
    @CrossOrigin
    @GetMapping(value = "/all-users")
    public @ResponseBody ArrayList<User> getAllUsers(){
        return userService.getAllUsers();
    }


    @CrossOrigin
    @GetMapping(value = "user-id/{id}")
    public @ResponseBody User getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @CrossOrigin
    @GetMapping(value = "user-username/{username}")
    public @ResponseBody User getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    //endregion

    //region Modify User
    @CrossOrigin
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(value = "/edit", consumes="application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User editUser(@RequestHeader("Authorization") String token, @RequestBody EditUserRequest request) {
        Principal principal = tokenService.noTokenThrow(token);

        return userService.UpdateUser(principal.getId(), request);
    }

    //endregion

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
