package com.revature.trailmates.auth;
import com.revature.trailmates.auth.dtos.requests.LoginRequest;
import com.revature.trailmates.auth.dtos.requests.NewUserRequest;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/auth")
public class AuthController {

    //todo fix authcontroller and principal class when user is added
    //todo talk about email verification and account activation

    @Inject
    private final AuthService authService;
    private final TokenService tokenService;

    @Inject
    @Autowired
    public AuthController(AuthService authService,TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    /**
     * Returns a principal containing the login token when given an appropriate login request
     * @param request A JSON object containing the username and password of the user
     * @param resp The servelet response that the header will be
     * @return Returns a principal with a token
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Principal login(@RequestBody LoginRequest request, HttpServletResponse resp) {
        Principal principal = new Principal(authService.login(request));
        String token = tokenService.generateToken(principal);
        principal.setToken(token);
        resp.setHeader("Authorization", token);
        return principal;
    }

    /**
     * Creates a new user in the users table based on the attributes of the newUserRequest JSON
     * @param newUserRequest A JSON object containing the details to create a new user
     */
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/newuser",consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody NewUserRequest newUserRequest){
        authService.register(newUserRequest);
    }
}
