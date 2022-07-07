package com.revature.trailmates.trailhistory;

import com.revature.trailmates.trailhistory.dto.requests.NewHistoryRequest;
import com.revature.trailmates.trailhistory.dto.response.History;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class TrailHistoryServiceTest {

    @Mock
    private TrailHistoryRepository repo;

    @InjectMocks
    private TrailHistoryService service;

    @Spy
    NewHistoryRequest newHistory;

    @Spy
    History history;

    @Test
    void getAscHistory() {
        List<History> ascHistory = List.of(history);

    }

    @Test
    void getDescHistory() {
    }

    @Test
    void insertNewHistory() {
    }
}