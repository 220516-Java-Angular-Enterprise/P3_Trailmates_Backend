package com.revature.trailmates.group;


import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.group.dto.requests.JoinGroupRequest;
import com.revature.trailmates.group.dto.requests.NewGroupRequest;
import com.revature.trailmates.user.User;
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
@RequestMapping("/group")
public class GroupController {

    @Inject
    @Autowired
    private GroupService service;

    @Inject
    @Autowired
    private TokenService tokenService;

    public GroupController() {
    }

    //show all users on a group
    //edit group
    //delete group

    @ResponseStatus(HttpStatus.ACCEPTED)
    @CrossOrigin
    @PostMapping(path = "/newGroup", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    NewGroupRequest creatingNewGroup(@RequestHeader("Authorization") String token, @RequestBody NewGroupRequest newGroup){
        Principal user = tokenService.noTokenThrow(token);
        service.createNewGroup(user.getId(), newGroup.getName());
        return newGroup;
    }

    @CrossOrigin
    @GetMapping(path = "/allgroups", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Group> allGroups(@RequestHeader("Authorization") String token){
        Principal user = tokenService.noTokenThrow(token);
        return null;
    }

    @CrossOrigin
    @PostMapping(path = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    User joinGroup(@RequestHeader("Authorization") String token, @RequestBody JoinGroupRequest join){
        Principal user = tokenService.noTokenThrow(token);
        service.addUserToGroup(user.getId(), join.getGroupName());
        return null;
    }

    @CrossOrigin
    @PutMapping(path = "/editGroup/{groupName}/{editName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void editGroup(@RequestHeader("Authorization") String token,@PathVariable String groupName ,@PathVariable String editName){
        Principal user = tokenService.noTokenThrow(token);
        service.editGroup(editName, groupName);
    }

    @CrossOrigin
    @DeleteMapping(path = "/leaveGroup/{groupName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void removeUser(@RequestHeader("Authorization") String token, @PathVariable String groupName){
        Principal user = tokenService.noTokenThrow(token);
        service.removeUserFromGroup(user.getId(), groupName);
    }

    @CrossOrigin
    @GetMapping(path = "/getUsers/{groupName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> getUsersFromGroup(@RequestHeader("Authorization") String token, @PathVariable String groupName){
        Principal user = tokenService.noTokenThrow(token);
        return service.getUsers(groupName);
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
