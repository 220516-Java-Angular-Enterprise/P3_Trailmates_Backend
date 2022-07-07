package com.revature.trailmates.trailhistory;

import com.revature.trailmates.trailhistory.dto.requests.NewHistory;
import com.revature.trailmates.trailhistory.dto.response.History;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class TrailHistoryService {

    @Inject
    @Autowired
    private TrailHistoryRepository repo;

    public TrailHistoryService() {
    }

    public List<History> getAscHistory(String userID){
        return repo.getAscHistory(userID);
    }

    public List<TrailHistory> getDescHistory(String userID){
        return repo.getDescHistory(userID);
    }

    public TrailHistory insertNewHistory(NewHistory newHistory, String userID){
        repo.addNewHistory(UUID.randomUUID().toString(), newHistory.getComment(), newHistory.getDate(), newHistory.getTrail_name(), userID);
        return null;
    }

}
