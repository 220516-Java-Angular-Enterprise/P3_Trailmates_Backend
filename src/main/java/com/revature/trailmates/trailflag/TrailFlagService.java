package com.revature.trailmates.trailflag;

import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.friends.Friend;
import com.revature.trailmates.friends.FriendService;
import com.revature.trailmates.notifications.NotificationService;
import com.revature.trailmates.notifications.dto.NewNotificationRequest;
import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.AuthenticationException;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
@Service
@Transactional
public class TrailFlagService {
    @Inject
    private final TrailFlagRepository trailFlagRepository;
    private final FriendService friendService;
    private final NotificationService notificationService;

    @Autowired
    public TrailFlagService(TrailFlagRepository trailFlagRepository, FriendService friendService, NotificationService notificationService) {
        this.trailFlagRepository = trailFlagRepository;
        this.friendService = friendService;
        this.notificationService = notificationService;
    }



    public Optional<List<TrailFlag>> getAllByDateIntAndUserId(long dateInt, String userId){
        Optional<List<TrailFlag>> returnList =  trailFlagRepository.getAllByDateIntAndUserId(dateInt,userId);
        if (!returnList.isPresent()||returnList.get().isEmpty()){
            throw new InvalidRequestException("Could not retrieve any flags matching the given date and user.");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllByDateIntAndTrailId(long dateInt, String trailId){
        Optional<List<TrailFlag>> returnList =  trailFlagRepository.getAllByDateIntAndTrailId(dateInt,trailId);
        if (!returnList.isPresent()||returnList.get().isEmpty()){
            throw new InvalidRequestException("Could not retrieve any results for the given date and trail.");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllByUserIdAndTrailId(String userId, String trailId){
        Optional<List<TrailFlag>> returnList =  trailFlagRepository.getAllByUserIdAndTrailId(userId,trailId);
        if (!returnList.isPresent()||returnList.get().isEmpty()){
            throw new InvalidRequestException("Could not retrieve any results for the given user and trail.");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllByUserId(String userId){
        Optional<List<TrailFlag>> returnList = trailFlagRepository.getAllByUserId(userId);
        if (!returnList.isPresent()||returnList.get().isEmpty()){
            throw new InvalidRequestException("Could not retrieve any results for the given user");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllActiveByUserId(String userId){
        long todayteInt=new Date().getTime()/(1000*60*60*24);
        Optional<List<TrailFlag>> returnList = trailFlagRepository.getAllActiveByUserId(userId,todayteInt);
        if (!returnList.isPresent()||returnList.get().isEmpty()){
            throw new InvalidRequestException("Could not retrieve any active results for the given user");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllByTrailId(String trailId){
        Optional<List<TrailFlag>> returnList = trailFlagRepository.getAllByTrailId(trailId);
        if (!returnList.isPresent()||returnList.get().isEmpty()){
            throw new InvalidRequestException("Could not retrieve any results for the given trail.");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllActiveByTrailId(String trailId){
        long todayteInt=new Date().getTime()/(1000*60*60*24);
        Optional<List<TrailFlag>> returnList = trailFlagRepository.getAllActiveByTrailId(trailId, todayteInt);
        if (!returnList.isPresent()||returnList.get().isEmpty()){
            throw new InvalidRequestException("Could not retrieve any active results for the given trail.");
        } else return returnList;
    }
    public TrailFlag saveNewTrailFlag(NewTrailFlagRequest request, Principal user){
        //if request has null fields, throw exception
        if (!nullChecker(request).isEmpty()){throw new InvalidRequestException(nullChecker(request));}
        //if flag is for a date earlier than today's date, throw exception.
        long todayteInt=new Date().getTime()/(1000*60*60*24);
        if (request.getDateInt()<todayteInt){
            throw new InvalidRequestException("Cannot create a flag for a previous date.");
        }
        //create a new trail flag using the request and the ID from the principal
        TrailFlag newFlag = new TrailFlag(request, user);
        //if flag already exists, throw exception
        if (isDuplicateFlag(newFlag)){
            throw new ResourceConflictException("Flag already exists for this user on this trail on this date.");
        }
        //try to save.  If it fails, throw exception explaining that either key constraints failed or database inaccessible
        try {
            trailFlagRepository.save(newFlag);
        }catch (Exception e){
            throw new InvalidRequestException("Unable to save trail flag.  Either the user or trail id were not found in the database, or the database was inaccessible.");
        }
        //Sending a notification out to all the People that are friends with the User
        List<Friend> friends = friendService.getAllFriendsFromFriendID(user.getId());
        for ( Friend f : friends ) {
            NewNotificationRequest request1 = new NewNotificationRequest();
            request1.setNotification_type("FLAG");
            //String[] date = String.valueOf(request.getDateInt() * (1000 * 60 * 60 * 24)).split("\\s+");
            request1.setMessage(user.getUsername() + " has marked that they are going hiking on " + new SimpleDateFormat("MM/dd/yyyy").format(Timestamp.from(Instant.now())));
            request1.setTarget_id(request.getTrailId());
            notificationService.addNotification(request1, f.getUser_id().getId());
        }

        return newFlag;
    }
    public boolean isDuplicateFlag(TrailFlag flag){
        Optional<List<TrailFlag>> returnList = trailFlagRepository.getAllByDateIntAndUserIdAndTrailId(flag.getDateInt(),flag.getUserId().getId(),flag.getTrailId().getId());
        if (!returnList.isPresent()||returnList.get().isEmpty()){
            return false;
        } else return true;
    }
    public boolean deleteTrailFlag(String id, Principal user){

        //get an optional of the flag from the database
        Optional<TrailFlag> markedFlag = trailFlagRepository.findById(id);
        //throw exception if flag is not found
        if (!markedFlag.isPresent()){
            throw new InvalidRequestException("Could not find a flag with that ID in order to delete it.");
        }
        //throw exception if the user deleting the flag is not the one who created it
        if (!markedFlag.get().getUserId().getId().equals(user.getId())){
            throw new AuthenticationException("You may only delete flags that you created.");
        }
        //try to delete flag.  If it fails, explain in the error message.
        try {
            trailFlagRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new InvalidRequestException("The selected flag was unable to be deleted.");
        }

    }
    private String nullChecker(NewTrailFlagRequest request){
        String eMessage = "";
        //Checks if any fields are null and builds a message accordingly
        try {
            Field[] fields = com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(request) == null) {
                    if(!eMessage.isEmpty()){
                        eMessage += ", ";
                    }
                    eMessage += field.getName() + " is null";
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return eMessage;
    }
}
