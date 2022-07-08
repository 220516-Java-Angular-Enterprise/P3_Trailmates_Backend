package com.revature.trailmates.trailflag;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;


import com.revature.trailmates.trailflag.dtos.requests.GetAllByDateIntAndTrailIdRequest;
import com.revature.trailmates.trailflag.dtos.requests.GetAllByDateIntAndUserIdRequest;
import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TrailFlagServiceTest {
    @Mock
    private TrailFlagRepository repo;
    @InjectMocks
    private TrailFlagService service;
    @Spy
    GetAllByDateIntAndUserIdRequest getAllByDateIntAndUserIdRequest = new GetAllByDateIntAndUserIdRequest();
    @Spy
    GetAllByDateIntAndTrailIdRequest getAllByDateIntAndTrailIdRequest = new GetAllByDateIntAndTrailIdRequest();
    @Spy
    NewTrailFlagRequest newTrailFlagRequest = new NewTrailFlagRequest();

    @Spy
    TrailFlag dummyFlag = new TrailFlag();

    @Test
    void getAllByDateIntAndUserId() {
        //empty list should 404, so mock an empty list when calling DB
        Mockito.when(repo.getAllByDateIntAndUserId(anyLong(),any())).thenReturn(Optional.of(new ArrayList<TrailFlag>()));
        assertThrows(InvalidRequestException.class, () -> service.getAllByDateIntAndUserId(dummyFlag.getDate_int(),dummyFlag.getUser_id()));
    }

    @Test
    void getAllByUserId() {
        //empty list should 404, so mock an empty list when calling DB
        Mockito.when(repo.getAllByUserId(any())).thenReturn(Optional.of(new ArrayList<TrailFlag>()));
        assertThrows(InvalidRequestException.class, () -> service.getAllByUserId(dummyFlag.getUser_id()));

    }
    @Test
    void getAllByTrailId() {
        //empty list should 404, so mock an empty list when calling DB
        Mockito.when(repo.getAllByTrailId(any())).thenReturn(Optional.of(new ArrayList<TrailFlag>()));
        assertThrows(InvalidRequestException.class, () -> service.getAllByTrailId(dummyFlag.getTrail_id()));

    }
    @Test
    //only fails if new trail flag has null fields or is a duplicate
    //throws exception if any fields to be saved are null
    void saveNewTrailFlag() {
        //assert throws exception when new flag has null fields.
        Exception e = assertThrows(RuntimeException.class, () ->service.saveNewTrailFlag(newTrailFlagRequest));
        assertTrue(e.getMessage().contains("is null"));
        //assert throws exception if new flag is duplicate (i.e. isDuplicateFlag=true)
        //initialize dummy flag to avoid null pointers
        newTrailFlagRequest.setTrail_id("foo");
        newTrailFlagRequest.setUser_id("bar");
        newTrailFlagRequest.setDate_int(100L);
        dummyFlag= new TrailFlag(newTrailFlagRequest);
        System.out.println(newTrailFlagRequest);
        //initialize the dummy list we return
        ArrayList<TrailFlag> dummyList = new ArrayList<TrailFlag>();
        dummyList.add(dummyFlag);
        //"get" dummylist from database
        Mockito.when(repo.getAllByDateIntAndUserIdAndTrailId(dummyFlag.getDate_int(),dummyFlag.getUser_id(),dummyFlag.getTrail_id())).thenReturn(Optional.of(dummyList));
        //if returned list is populated, we have a duplicate.  Throw exception.
        assertThrows(ResourceConflictException.class, () -> service.saveNewTrailFlag(newTrailFlagRequest));
    }
    @Test
    //returns false if no matching flag is found with given parameters
    void isDuplicateFlag() {
        //mock an empty list when searching for dummy flag
        Mockito.when(repo.getAllByDateIntAndUserIdAndTrailId(dummyFlag.getDate_int(),dummyFlag.getUser_id(),dummyFlag.getTrail_id())).thenReturn(Optional.of(new ArrayList<TrailFlag>()));
        assertEquals(false,service.isDuplicateFlag(dummyFlag));
    }
    @Test
    //Throws exception if could not delete flag
    void deleteTrailFlag() {
        //verify that method uses repo deleteById method.  Will throw exception in this case.
        Mockito.doThrow(new InvalidRequestException()).when(repo).deleteById(any());
        assertThrows(InvalidRequestException.class, ()->service.deleteTrailFlag(dummyFlag.getId()));
    }

    @Test
    void getAllByDateIntAndTrailId() {
        //empty list should 404, so mock an empty list when calling DB
        Mockito.when(repo.getAllByDateIntAndTrailId(anyLong(),any())).thenReturn(Optional.of(new ArrayList<TrailFlag>()));
        assertThrows(InvalidRequestException.class, () -> service.getAllByDateIntAndTrailId(dummyFlag.getDate_int(),dummyFlag.getTrail_id()));
    }
}