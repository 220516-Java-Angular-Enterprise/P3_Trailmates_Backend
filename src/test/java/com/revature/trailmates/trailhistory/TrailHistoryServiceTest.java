package com.revature.trailmates.trailhistory;


import com.revature.trailmates.trailhistory.dto.requests.NewHistoryRequest;
import com.revature.trailmates.trailhistory.dto.response.History;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


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


    }

    @Test
    void getDescHistory() {
    }

    @Test
    void insertWrongTrailName() {
        newHistory.setComment("demo");
        newHistory.setTrail_name("asd");
        newHistory.setDate("2022-01-10 13:45:00.0");
        assertThrows(InvalidRequestException.class, () -> service.insertNewHistory(newHistory, "0c2b4bc1-7270-4264-96c0-7d897fbd1771"));
    }

    @Test
    void insertWrongDate() {
        newHistory.setComment("demo");
        newHistory.setTrail_name("asd");
        newHistory.setDate("2022-12-10 13:45:00.0");
        assertThrows(InvalidRequestException.class, () -> service.insertNewHistory(newHistory, "0c2b4bc1-7270-4264-96c0-7d897fbd1771"));
    }



}