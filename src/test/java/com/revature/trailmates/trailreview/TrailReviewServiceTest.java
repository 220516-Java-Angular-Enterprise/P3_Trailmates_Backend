package com.revature.trailmates.trailreview;

import com.revature.trailmates.trailreview.dtos.requests.TrailReviewRequest;
import com.revature.trailmates.trailreview.dtos.responses.TrailAverageRating;
import com.revature.trailmates.trails.TrailService;
import com.revature.trailmates.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void deleteReview() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void getAverageReviewsForTrail() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void getAllReviewsForTrail() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void getReviewByTrailIDAndUserID() {
        // Arrange

        // Act

        // Assert
    }
}