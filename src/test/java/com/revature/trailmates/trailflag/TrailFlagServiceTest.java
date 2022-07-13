package com.revature.trailmates.trailflag;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;


import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
import com.revature.trailmates.util.custom_exception.AuthenticationException;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TrailFlagServiceTest {
    @Mock
    private TrailFlagRepository repo;
    @InjectMocks
    private TrailFlagService service;
    @Spy
    NewTrailFlagRequest newTrailFlagRequest = new NewTrailFlagRequest();
    @Spy
    TrailFlag dummyFlag = new TrailFlag();
    @Spy
    Principal dummyPrincipal = new Principal("dummy id","dummy username","dummy role");
    private static long todayteInt = new Date().getTime()/(1000*60*60*24);
    //todo: should look up how to set up a BeforeEach annotation to clean up these tests and avoid repeating variable assignments
    @Test
    void getAllByDateIntAndUserId() {
        //empty list should 404, so mock an empty list when calling DB
        Mockito.when(repo.getAllByDateIntAndUserId(anyLong(),any())).thenReturn(Optional.empty());
        assertThrows(InvalidRequestException.class, () -> service.getAllByDateIntAndUserId(dummyFlag.getDateInt(),dummyFlag.getUserId().getId()));
    }

    @Test
    void getAllByUserId() {
        //empty list should 404, so mock an empty list when calling DB
        Mockito.when(repo.getAllByUserId(any())).thenReturn(Optional.empty());
        assertThrows(InvalidRequestException.class, () -> service.getAllByUserId(dummyFlag.getUserId().getId()));
    }
    @Test
    void getAllByTrailId() {
        //empty list should 404, so mock an empty list when calling DB
        Mockito.when(repo.getAllByTrailId(any())).thenReturn(Optional.empty());
        assertThrows(InvalidRequestException.class, () -> service.getAllByTrailId(dummyFlag.getTrailId().getId()));
    }
    @Test
    void getAllByUserIdAndTrailId() {
        //empty list should 404, so mock an empty list when calling DB
        Mockito.when(repo.getAllByUserIdAndTrailId(any(),any())).thenReturn(Optional.empty());
        assertThrows(InvalidRequestException.class, () -> service.getAllByUserIdAndTrailId(dummyFlag.getUserId().getId(),dummyFlag.getTrailId().getId()));
    }

    @Test
    //save fails when flag is a duplicate
    void saveNewTrailFlagResourceConflict() {

        //assert throws exception if new flag is duplicate (i.e. isDuplicateFlag=true)
        //initialize dummy flag to avoid null pointers
        newTrailFlagRequest.setTrailId("foo");
        dummyPrincipal.setId("bar");
        newTrailFlagRequest.setDateInt(todayteInt+1);
        dummyFlag= new TrailFlag(newTrailFlagRequest, dummyPrincipal);
        //initialize the dummy list we return
        ArrayList<TrailFlag> dummyList = new ArrayList<TrailFlag>();
        dummyList.add(dummyFlag);
        //"get" dummylist from database
        Mockito.when(repo.getAllByDateIntAndUserIdAndTrailId(dummyFlag.getDateInt(),dummyFlag.getUserId().getId(),dummyFlag.getTrailId().getId())).thenReturn(Optional.of(dummyList));
        //if returned list is populated, we have a duplicate.  Throw exception.
        assertThrows(ResourceConflictException.class, () -> service.saveNewTrailFlag(newTrailFlagRequest, dummyPrincipal));
    }
    @Test
    //save fails when request has null fields
    void saveNewTrailFlagNullFields() {
        //assert throws exception when new flag has null fields.
        Exception e = assertThrows(RuntimeException.class, () ->service.saveNewTrailFlag(newTrailFlagRequest, dummyPrincipal));
        assertTrue(e.getMessage().contains("is null"));
    }
    @Test
    //save fails when repository is inaccessible or foreign key constraints not followed
    void saveNewTrailFlagSQLException(){
        //assert throws exception if we get an SQL exception due to user_id or trail_id not matching in database
        newTrailFlagRequest.setTrailId("foo");
        dummyPrincipal.setId("bar");
        newTrailFlagRequest.setDateInt(todayteInt+1);
        dummyFlag= new TrailFlag(newTrailFlagRequest, dummyPrincipal);
        //throw an exception when trying to save this data
        Mockito.when(repo.save(dummyFlag)).thenThrow(new RuntimeException("An exception occurred when saving"));
        Exception e = assertThrows(InvalidRequestException.class,()->service.saveNewTrailFlag(newTrailFlagRequest, dummyPrincipal));
        assertEquals("Unable to save trail flag.  Either the user or trail id were not found in the database, or the database was inaccessible.",e.getMessage());
    }
    @Test
    //save fails when dateInt is earlier than current date
    void saveNewTrailFlagBadDateInt(){
        newTrailFlagRequest.setTrailId("foo");
        dummyPrincipal.setId("bar");
        dummyFlag= new TrailFlag(newTrailFlagRequest, dummyPrincipal);
        newTrailFlagRequest.setDateInt(todayteInt-1);
        Exception e = assertThrows(InvalidRequestException.class, () -> service.saveNewTrailFlag(newTrailFlagRequest,dummyPrincipal));
        assertEquals("Cannot create a flag for a previous date.",e.getMessage());
    }
    @Test
    //returns false if no matching flag is found with given parameters
    void isDuplicateFlag() {
        //mock an empty list when searching for dummy flag
        Mockito.when(repo.getAllByDateIntAndUserIdAndTrailId(dummyFlag.getDateInt(),dummyFlag.getUserId().getId(),dummyFlag.getTrailId().getId())).thenReturn(Optional.of(new ArrayList<TrailFlag>()));
        assertEquals(false,service.isDuplicateFlag(dummyFlag));
    }
    @Test
    //Throws exception if could not find flag
    void deleteTrailFlagNotFound() {
        Mockito.doReturn(Optional.empty()).when(repo).findById(any());
        Exception e = assertThrows(InvalidRequestException.class, ()->service.deleteTrailFlag(dummyFlag.getId(), dummyPrincipal));
        assertEquals("Could not find a flag with that ID in order to delete it.",e.getMessage());

    }
    @Test
    //Throws exception if deleting user doesn't match flag
    void deleteTrailFlagUserMismatch(){
        //initialize dummy user
        dummyPrincipal.setId("fool");
        //initialize dummy flag
        dummyFlag = new TrailFlag();
        dummyFlag.getUserId().setId("foo");
        dummyFlag.setDateInt(todayteInt+1);
        dummyFlag.setId("bar");
        dummyFlag.getTrailId().setId("baz");
        //mock database returns dummy flag
        Mockito.when(repo.findById(any())).thenReturn(Optional.of(dummyFlag));
        Exception e = assertThrows(AuthenticationException.class, ()->service.deleteTrailFlag(dummyFlag.getId(), dummyPrincipal));
    }
    @Test
    void getAllByDateIntAndTrailId() {
        //empty list should 404, so mock an empty list when calling DB
        Mockito.when(repo.getAllByDateIntAndTrailId(anyLong(),any())).thenReturn(Optional.of(new ArrayList<TrailFlag>()));
        assertThrows(InvalidRequestException.class, () -> service.getAllByDateIntAndTrailId(dummyFlag.getDateInt(),dummyFlag.getTrailId().getId()));
    }


}