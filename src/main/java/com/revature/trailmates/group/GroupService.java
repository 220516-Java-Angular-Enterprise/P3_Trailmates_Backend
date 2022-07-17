package com.revature.trailmates.group;


import com.revature.trailmates.user.User;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class GroupService {

    @Inject
    @Autowired
    private GroupRepository repo;

    public GroupService() {
    }

    public List<Group> getAllGroups(){
        return repo.getAllGroups();
    }

    public void createNewGroup(String userID, String groupName){
        if(isDuplicateGroup(groupName)) throw new ResourceConflictException("Group already exists.");
        String groupID = UUID.randomUUID().toString();
        String conversationID = UUID.randomUUID().toString();
        repo.createGroup(groupID, conversationID, groupName);
        //adding the founder to the group
        repo.addUser(groupID,userID);
    }

    public void addUserToGroup(String userID, String groupName){
        if(groupDoesntExists(groupName)) throw new InvalidRequestException("Group doesn't exist.");
        if(isDuplicateUser(userID, groupName)) throw new ResourceConflictException("You are already in this group.");
        repo.addUser(repo.retrieveGroupID(groupName), userID);
    }

    public void removeUserFromGroup(String userID, String groupName){
        if(groupDoesntExists(groupName)) throw new InvalidRequestException("Group doesn't exist.");
        repo.removeUser(userID, repo.retrieveGroupID(groupName));
    }

    public void editGroup(String editName, String groupName){
        if(editName.equals(groupName)) throw new ResourceConflictException("Group name needs to be different.");
        if(isDuplicateGroup(editName)) throw new ResourceConflictException("Name is already taken.");
        repo.editGroupName(editName, repo.retrieveGroupID(groupName));
    }

    public List<User> getUsers(String groupName){
        if(groupDoesntExists(groupName)) throw new InvalidRequestException("Group doesn't exist.");
        return repo.getUsers(repo.retrieveGroupID(groupName)).getUsers();
    }

    private boolean groupDoesntExists(String groupName){
        return repo.retrieveGroupID(groupName) == null;
    }

    private boolean isDuplicateUser(String userID, String groupName){
        return repo.duplicateUser(userID, repo.retrieveGroupID(groupName)) !=null;
    }

    private boolean isDuplicateGroup(String groupName){
        return repo.duplicateGroup(groupName) !=null;
    }


}
