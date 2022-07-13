package com.revature.trailmates.trailhistory;

import com.revature.trailmates.trailhistory.dto.requests.NewHistoryRequest;
import com.revature.trailmates.trailhistory.dto.response.History;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class TrailHistoryService {

    @Inject
    @Autowired
    private TrailHistoryRepository repo;

    public TrailHistoryService() {
    }

    public List<History> getAscHistory(String userID){
        return repo.getAscHistory(userID).stream().map(e -> new History().extractTrail(e)).collect(Collectors.toList());
    }

    public List<History> getDescHistory(String userID){
        return repo.getDescHistory(userID).stream().map(e -> new History().extractTrail(e)).collect(Collectors.toList());
    }

    public TrailHistory getHistory(String id) {
        return repo.getHistory(id);
    }

    public void insertNewHistory(NewHistoryRequest newHistory, String userID){
        Timestamp date = Timestamp.valueOf(newHistory.getDate().replaceAll("[A-Z]", " "));
        if(!correctDate(date)) throw new InvalidRequestException("Invalid Date");
        String trailID = repo.trailID(newHistory.getTrail_name());
        if(trailID == null) throw new InvalidRequestException("Trail doesn't exist");
        repo.addNewHistory(UUID.randomUUID().toString(), newHistory.getComment(), date, trailID, userID);
    }

    private boolean correctDate(Timestamp date){
        return date.getTime() <= System.currentTimeMillis();
    }

    /*private boolean isDuplicateHistory(Timestamp date, String trail_id){
        return repo.isDuplicateHistory(date, trail_id) != null;
    }*/

}
