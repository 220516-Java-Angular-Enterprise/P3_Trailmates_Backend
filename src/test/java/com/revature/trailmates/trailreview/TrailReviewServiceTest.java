package com.revature.trailmates.trailreview;

import com.revature.trailmates.trailreview.dtos.requests.TrailReviewRequest;
import com.revature.trailmates.trailreview.dtos.responses.TrailAverageRating;
import com.revature.trailmates.trails.TrailService;
import com.revature.trailmates.user.UserService;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
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

    @Spy
    private TrailReviewAverage trailReviewAverage;

    private String userID = "1";
    private String trailID = "2";


    @Test
    void newTrailReview() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void updateTrailReview() {
        // Arrange

        // Act

        // Assert
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
        Mockito.doNothing().when(trailService.getTrail(trailID));
        //Mockito.when(trailReviewRepository.avgRating(trailID)).thenReturn(trailReviewAverage);

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> trailReviewService.getAverageReviewsForTrail(trailID));
        String expectedMessage = "Cannot find trail";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.equals(expectedMessage));
    }*/

   /* @Test
    void getAllReviewsForTrail() {
        // Arrange
        Mockito.when(trailService.getTrail(trailID)).thenThrow(new InvalidRequestException("Could not retrieve any results for the provided query."));
        // Act

        // Assert
    }*/

    @Test
    void getReviewByTrailIDAndUserID() {
        // Arrange

        // Act

        // Assert
    }
}