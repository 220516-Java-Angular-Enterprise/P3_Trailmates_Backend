package com.revature.trailmates.trailflag;

import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.AuthenticationException;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;
@Service
@Transactional
public class TrailFlagService {
    @Inject
    private final TrailFlagRepository trailFlagRepository;
    @Autowired
    public TrailFlagService(TrailFlagRepository trailFlagRepository){
        this.trailFlagRepository=trailFlagRepository;
    }
    public Optional<List<TrailFlag>> getAllByDateIntAndUserId(long dateInt, String userId){
        Optional<List<TrailFlag>> returnList =  trailFlagRepository.getAllByDateIntAndUserId(dateInt,userId);
        if (!returnList.isPresent()||returnList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any flags matching the given date and user.");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllByDateIntAndTrailId(long dateInt, String trailId){
        Optional<List<TrailFlag>> returnList =  trailFlagRepository.getAllByDateIntAndTrailId(dateInt,trailId);
        if (!returnList.isPresent()||returnList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any results for the given date and trail.");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllByUserIdAndTrailId(String userId, String trailId){
        Optional<List<TrailFlag>> returnList =  trailFlagRepository.getAllByUserIdAndTrailId(userId,trailId);
        if (!returnList.isPresent()||returnList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any results for the given user and trail.");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllByUserId(String userId){
        Optional<List<TrailFlag>> returnList = trailFlagRepository.getAllByUserId(userId);
        if (!returnList.isPresent()||returnList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any results for the given user");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllByTrailId(String trailId){
        Optional<List<TrailFlag>> returnList = trailFlagRepository.getAllByTrailId(trailId);
        if (!returnList.isPresent()||returnList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any results for the given trail.");
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
        return newFlag;
    }
    public boolean isDuplicateFlag(TrailFlag flag){
        Optional<List<TrailFlag>> returnList = trailFlagRepository.getAllByDateIntAndUserIdAndTrailId(flag.getDateInt(),flag.getUserId().getId(),flag.getTrailId().getId());
        if (!returnList.isPresent()||returnList.get().size()==0){
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
        if (!markedFlag.get().getUserId().equals(user.getId())){
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
