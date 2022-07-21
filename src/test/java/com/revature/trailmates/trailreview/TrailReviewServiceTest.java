package com.revature.trailmates.trailreview;

import com.revature.trailmates.trailreview.dtos.requests.TrailReviewRequest;
import com.revature.trailmates.trailreview.dtos.responses.TrailAverageRating;
import com.revature.trailmates.trails.Trail;
import com.revature.trailmates.trails.TrailService;
import com.revature.trailmates.user.User;
import com.revature.trailmates.user.UserService;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TrailReviewServiceTest {

    @Mock
    private TrailReviewRepository trailReviewRepository;

    @Mock
    private TrailService trailService;

    @Mock
    private UserService userService;//Might not be necessary to have

    @InjectMocks
    private TrailReviewService trailReviewService;

    @Spy
    private TrailReviewRequest trailReviewRequest;

    @Spy
    private TrailAverageRating trailAverageRating;

    @Mock
    private TrailReviewAverage trailReviewAverage;

    private String userID = "1";
    private String trailID = "2";


    @Test
    void newTrailReviewSuccess() {
        // Arrange
        trailReviewRequest = new TrailReviewRequest();
        trailReviewRequest.setRating(BigDecimal.valueOf(3));
        trailReviewRequest.setComment("test comment");
        Mockito.when(userService.getUserById(userID)).thenReturn(new User());
        //Mockito.when(trailService.getTrail(trailID)).thenThrow(new InvalidRequestException("Could not retrieve any results for the provided query."));

        // Act
        trailReviewService.newTrailReview(trailReviewRequest,userID,trailID);

        // Assert
        Mockito.verify(trailReviewRepository, times(1)).save(any());
    }

    @Test
    void updateTrailReviewSuccess() {
        // Arrange
        TrailReview trailReview = new TrailReview();
        // Act


        // Assert
        //Mockito.verify()
    }

    //region deleteReview tests
    @Test
    void deleteReviewDoesNotExist() {
        // Arrange
        Mockito.when(trailReviewRepository.ifReviewExists(userID, trailID)).thenReturn(null);


        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> trailReviewService.deleteReview(userID, trailID));
        String expectedMessage = "Trail Review does not exist";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.equals(expectedMessage));
    }
    @Test
    void deleteReviewDoesExist() {
        // Arrange
        TrailReview trailReview = new TrailReview();
        Mockito.when(trailReviewRepository.ifReviewExists(userID, trailID)).thenReturn(trailReview);

        // Act
        trailReviewService.deleteReview(userID, trailID);

        // Assert
        Mockito.verify(trailReviewRepository).deleteTrailReview(userID, trailID);
    }
    //endregion

    //region getAverageReviewsForTrail
    @Test
    void getAverageReviewsForTrailTrailDoesNotExist() {
        // Arrange
        Mockito.when(trailService.getTrail(trailID)).thenThrow(new InvalidRequestException("Could not retrieve any results for the provided query."));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> trailReviewService.getAverageReviewsForTrail(trailID));
        String expectedMessage = "Cannot find trail";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.equals(expectedMessage));
    }

    /*@Test
    void getAverageReviewsForTrailTrailDoesExist() {
        // Arrange
        //Mockito.when(trailService.getTrail(trailID)).thenThrow(new InvalidRequestException("Could not retrieve any results for the provided query."));
        //TrailReviewAverage trailReviewAverage;
        Mockito.when(trailService.getTrail(trailID)).thenReturn(Optional.of(new Trail()));
        Mockito.when(trailAverageRating.getRatingAvg());
        //Mockito.when(trailReviewRepository.avgRating(trailID)).thenReturn(trailReviewAverage);

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> trailReviewService.getAverageReviewsForTrail(trailID));
        String expectedMessage = "Cannot find trail";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.equals(expectedMessage));
    }*/
    //endregion

    //region getAllReviewsForTrail tests
    @Test
    void getAllReviewsForTrailDoesNotExist() {
        // Arrange
        Mockito.when(trailService.getTrail(trailID)).thenThrow(new InvalidRequestException("Could not retrieve any results for the provided query."));

        // Act
        Exception exception = assertThrows(InvalidRequestException.class, () -> trailReviewService.getAllReviewsForTrail(trailID));
        String expectedMessage = "Cannot find trail";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.equals(expectedMessage));
    }

    @Test
    void getAllReviewsForTrailNoTrailReviews() {
        // Arrange
        //Mockito.when(trailService.getTrail(trailID)).thenThrow(new InvalidRequestException("Could not retrieve any results for the provided query."));
        //Mockito.doNothing().when(trailReviewRepository.findAll());

        // Act
        Exception exception = assertThrows(InvalidRequestException.class, () -> trailReviewService.getAllReviewsForTrail(trailID));
        String expectedMessage = "No reviews found";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.equals(expectedMessage));
    }

    /*@Test
    void getAllReviewsForTrailSuccess() {
        // Arrange
        //Mockito.when(trailService.getTrail(trailID)).thenThrow(new InvalidRequestException("Could not retrieve any results for the provided query."));
        List<TrailReview> expectedList = new ArrayList<>();
        TrailReview trailReview = new TrailReview();
        expectedList.add(trailReview);
        Mockito.when(trailReviewRepository.findAll()).thenReturn(Collections.singleton(trailReview));

        // Act
        List<TrailReview> actualList = trailReviewService.getAllReviewsForTrail(trailID);

        // Assert
        assertEquals(expectedList.toString(), actualList.toString());
    }*/
    //endregion

    //region getReviewByTrailIDAndUserID tests
    /*@Test
    void getReviewByTrailIDAndUserIDTrailDoesExist() {
        // Arrange
        //Mockito.when(trailReviewRepository.ifReviewExists(trailID, userID)).thenReturn(null);

        // Act
        Exception exception = assertThrows(InvalidRequestException.class, () -> trailReviewService.getAllReviewsForTrail(trailID));
        String expectedMessage = "Cannot find trail review";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.equals(expectedMessage));
    }*/

    @Test
    void getReviewByTrailIDAndUserIDSuccess() {
        // Arrange
        TrailReview expectedReview = new TrailReview();
        expectedReview.setComment("test comment");
        Mockito.when(trailReviewRepository.ifReviewExists(trailID, userID)).thenReturn(expectedReview);

        // Act
        TrailReview actualReview = trailReviewService.getReviewByTrailIDAndUserID(trailID, userID);

        // Assert
        assertEquals(expectedReview.getComment(), actualReview.getComment());
    }
    //endregion
}