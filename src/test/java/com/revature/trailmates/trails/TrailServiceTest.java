package com.revature.trailmates.trails;

import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TrailServiceTest {

    @Mock
    private TrailRepository trailRepository;
    @InjectMocks
    private TrailService trailService;

    @Spy
    Trail dummyTrail = new Trail();

    @Test
    void searchTrailByState() {

    }

    @Test
    void searchTrailByName() {
    }

    @Test
    void getAllTrailsPage() {
        Mockito.when(trailRepository.getAllTrails()).thenReturn(new ArrayList<Trail>());
        assertThrows(InvalidRequestException.class, () -> trailService.getAllTrailsPage(-1));
    }

    @Test
    void getTrail() {
        Mockito.when(trailRepository.findById(any())).thenReturn(Optional.of(new Trail()));
        assertThrows(InvalidRequestException.class, () -> trailService.getTrail(dummyTrail.getId()));
    }

    @Test
    void getAllTrails() {
    }

}