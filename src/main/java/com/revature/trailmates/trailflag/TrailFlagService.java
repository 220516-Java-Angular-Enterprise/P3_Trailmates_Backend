package com.revature.trailmates.trailflag;

import com.revature.trailmates.auth.dtos.requests.NewUserRequest;
import com.revature.trailmates.trailflag.dtos.requests.GetAllByDateIntAndTrailIdRequest;
import com.revature.trailmates.trailflag.dtos.requests.GetAllByDateIntAndUserIdRequest;
import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
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
    private final TrailFlagRepository trailFlagRepository;
    @Autowired
    public TrailFlagService(TrailFlagRepository trailFlagRepository){
        this.trailFlagRepository=trailFlagRepository;
    }
    public Optional<List<TrailFlag>> getAllByDateIntAndUserId(long dateInt, String user_id){
        Optional<List<TrailFlag>> returnList =  trailFlagRepository.getAllByDateIntAndUserId(dateInt,user_id);
        if (!returnList.isPresent()||returnList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any results for the provided query.");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllByDateIntAndTrailId(long dateInt, String trail_id){
        Optional<List<TrailFlag>> returnList =  trailFlagRepository.getAllByDateIntAndTrailId(dateInt,trail_id);
        if (!returnList.isPresent()||returnList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any results for the provided query.");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllByUserId(String user_id){
        Optional<List<TrailFlag>> returnList = trailFlagRepository.getAllByUserId(user_id);
        if (!returnList.isPresent()||returnList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any results for the provided query.");
        } else return returnList;
    }
    public Optional<List<TrailFlag>> getAllByTrailId(String trail_id){
        Optional<List<TrailFlag>> returnList = trailFlagRepository.getAllByTrailId(trail_id);
        if (!returnList.isPresent()||returnList.get().size()==0){
            throw new InvalidRequestException("Could not retrieve any results for the provided query.");
        } else return returnList;
    }
    public TrailFlag saveNewTrailFlag(NewTrailFlagRequest request){
        if (!nullChecker(request).isEmpty()){throw new InvalidRequestException(nullChecker(request));}
        TrailFlag newFlag = new TrailFlag(request);
        //if flag already exists, throw exception
        if (isDuplicateFlag(newFlag)){
            throw new ResourceConflictException("Flag already exists for this user on this trail on this date.");
        }
        //if the new flag has null values, throw exception
        trailFlagRepository.save(newFlag);
        return newFlag;
    }
    public boolean isDuplicateFlag(TrailFlag flag){
        Optional<List<TrailFlag>> returnList = trailFlagRepository.getAllByDateIntAndUserIdAndTrailId(flag.getDate_int(),flag.getUser_id(),flag.getTrail_id());
        if (!returnList.isPresent()||returnList.get().size()==0){
            return false;
        } else return true;
    }
    public boolean deleteTrailFlag(String id){
        try {
            trailFlagRepository.deleteById(id);
            return true;
        } catch (Exception e){
            throw new InvalidRequestException("Failed to delete trail flag.");
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
