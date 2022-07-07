package com.revature.trailmates.trailflag;

import static org.junit.jupiter.api.Assertions.*;


import com.revature.trailmates.trailflag.dtos.requests.GetAllByDateIntAndUserIdRequest;
import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

class TrailFlagServiceTest {
    @Mock
    private TrailFlagRepository repo;

    @InjectMocks
    private TrailFlagService service;

    @Spy
    GetAllByDateIntAndUserIdRequest GetAllByDateIntAndUserIdRequest;

    @Spy
    NewTrailFlagRequest newUserRequest;

    @org.junit.jupiter.api.Test
    void getAllByDateIntAndUserId() {

    }

    @org.junit.jupiter.api.Test
    void getAllByUserId() {
    }

    @org.junit.jupiter.api.Test
    void getAllByTrailId() {
    }

    @org.junit.jupiter.api.Test
    void saveNewTrailFlag() {
    }

    @org.junit.jupiter.api.Test
    void isDuplicateFlag() {
    }

    @org.junit.jupiter.api.Test
    void deleteTrailFlag() {
    }
}