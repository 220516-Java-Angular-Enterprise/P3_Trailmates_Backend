package com.revature.trailmates.trailflag;

import com.revature.trailmates.auth.dtos.requests.NewUserRequest;
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
    public Optional<List<TrailFlag>> getAllByDateIntAndUserId(GetAllByDateIntAndUserIdRequest request){
        return trailFlagRepository.getAllByDateIntAndUserId(request.getDate_int(),request.getUser_id());
    }
    public Optional<List<TrailFlag>> getAllByUserId(String user_id){
        return trailFlagRepository.getAllByUserId(user_id);
    }
    public Optional<List<TrailFlag>> getAllByTrailId(String trail_id){
        return trailFlagRepository.getAllByTrailId(trail_id);
    }
    public TrailFlag saveNewTrailFlag(NewTrailFlagRequest request){
        TrailFlag newFlag = new TrailFlag(request);
        //if flag already exists, throw exception
        if (isDuplicateFlag(newFlag)){
            throw new ResourceConflictException("Flag already exists for this user on this trail on this date.");
        }
        //if the new flag has null values, throw exception
        if (nullChecker(request)!=""){
            throw new InvalidRequestException(nullChecker(request));
        }
        trailFlagRepository.save(newFlag);
        return newFlag;

    }
    public boolean isDuplicateFlag(TrailFlag flag){
        if (trailFlagRepository.getAllByDateIntAndUserIdAndTrailId(flag.getDate_int(),flag.getUser_id(),flag.getTrail_id()).get().size()==0){
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
