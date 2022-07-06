package com.revature.trailmates.trailhistory;

import com.revature.trailmates.trailhistory.dto.response.History;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class TrailHistoryService {

    @Inject
    @Autowired
    private TrailHistoryRepository repo;

    public TrailHistoryService() {
    }

    public List<TrailHistory> getAscHistory(String userID){
        return repo.getAscHistory(userID);
    }

    public List<TrailHistory> getDescHistory(String userID){
        return repo.getDescHistory(userID);
    }


}
