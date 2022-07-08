package com.revature.trailmates.group;


import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@Service
public class GroupService {

    @Inject
    @Autowired
    private GroupRepository repo;

    public GroupService() {
    }

    public void createNewGroup(String userID, String groupName){
        String groupID = UUID.randomUUID().toString();
        String conversationID = UUID.randomUUID().toString();
        repo.createGroup(groupID, conversationID, groupName);
        //adding the founder
        repo.addUser(userID,groupID);

    }

    public void addUserToGroup(String userID, String groupName){
        repo.addUser(userID, repo.retrieveGroupID(groupName));
    }

    private boolean isDuplicateUser(String userID, String groupName){
        return repo.duplicateUser(userID, repo.retrieveGroupID(groupName)) !=null;
    }

    private boolean isDuplicateGroup(String groupName){
        return repo.duplicateGroup(repo.retrieveGroupID(groupName)) !=null;
    }


}
