package com.revature.trailmates.trailreview;

import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.requests.NewUserRequest;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trailreview")
public class TrailReviewController {

    @Inject
    private TrailReviewService trailReviewService;
    private TokenService tokenService;

    @Inject
    @Autowired
    public TrailReviewController(TrailReviewService trailReviewService, TokenService tokenService) {
        this.trailReviewService = trailReviewService;
        this.tokenService = tokenService;
    }

    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestHeader("Authorization") String token){
        Principal requester = tokenService.noTokenThrow(token);

    }
}
