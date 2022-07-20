package com.revature.trailmates.group;


import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.user.User;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * @param token verifying it is a user in the database
     * @param newGroup creates a new group
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CrossOrigin
    @PostMapping(path = "/newGroup/{newGroup}", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public void creatingNewGroup(@RequestHeader("Authorization") String token, @PathVariable String newGroup){
        Principal user = tokenService.noTokenThrow(token);
        service.createNewGroup(user.getId(), newGroup);
    }

    /**
     * @param token verifying it is a user in the database
     * @return returns a list of all the groups active
     */
    @CrossOrigin
    @GetMapping(path = "/allgroups", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Group> allGroups(@RequestHeader("Authorization") String token){
        Principal user = tokenService.noTokenThrow(token);
        return service.getAllGroups();
    }

    /**
     * @param token verifying it is a user in the database
     * @param join name of the group they want to join
     */
    @CrossOrigin
    @PostMapping(path = "/addUser/{join}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void joinGroup(@RequestHeader("Authorization") String token, @PathVariable String join){
        Principal user = tokenService.noTokenThrow(token);
        service.addUserToGroup(user.getId(), join);
    }

    /**
     * @param token verifying it is a user in the database
     * @param groupName group they want to edit
     * @param editName the name they want to change it to
     */
    @CrossOrigin
    @PutMapping(path = "/editGroup/{groupName}/{editName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void editGroup(@RequestHeader("Authorization") String token,@PathVariable String groupName ,@PathVariable String editName){
        Principal user = tokenService.noTokenThrow(token);
        service.editGroup(editName, groupName);
    }

    /**
     * @param token verifying it is a user in the database
     * @param groupName which group they want to leave
     */
    @CrossOrigin
    @DeleteMapping(path = "/leaveGroup/{groupName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeUser(@RequestHeader("Authorization") String token, @PathVariable String groupName){
        Principal user = tokenService.noTokenThrow(token);
        service.removeUserFromGroup(user.getId(), groupName);
    }

    /**
     * @param token verifying it is a user in the database
     * @param groupName the group they want to return
     * @return returning all users in that group
     */
    @CrossOrigin
    @GetMapping(path = "/getUsers/{groupName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> getUsersFromGroup(@RequestHeader("Authorization") String token, @PathVariable String groupName){
        Principal user = tokenService.noTokenThrow(token);
        return service.getUsers(groupName);
    }


}
