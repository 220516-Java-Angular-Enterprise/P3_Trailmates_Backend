package com.revature.trailmates.auth;

import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.user.User;
import com.revature.trailmates.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private TokenService tokenService;

    @Spy
    private Principal principalExpected;

    @Spy
    private Principal principalActual;

    @Spy
    private User user;

    @Spy
    private JwtConfig jwtConfig;

    @Test
    void extractRequesterDetails() {
        // Arrange
        principalExpected.setId("0");
        principalExpected.setUsername("test");
        principalExpected.setRole("tester");

        String token = tokenService.generateToken(principalExpected);

        principalActual.setId("0");
        principalActual.setUsername("test");
        principalActual.setRole("tester");

        // Act
        principalExpected = tokenService.extractRequesterDetails(token);

        // Assert
        assertEquals(principalExpected.getId(), principalActual.getId());
    }

    //region NoToken Tests
    @Test
    void noTokenThrowSuccess() {
        // Arrange
        principalExpected.setId("0");
        principalExpected.setUsername("test");
        principalExpected.setRole("tester");

        principalActual.setId("0");
        principalActual.setUsername("test");
        principalActual.setRole("tester");

        String token = tokenService.generateToken(principalActual);

        user = new User();
        Mockito.when(userService.getUserByUsername(principalActual.getUsername())).thenReturn(user);

        // Act
        principalActual = tokenService.noTokenThrow(token);

        // Assert
        assertEquals(principalExpected.getId(), principalActual.getId());
    }

    @Test
    void noTokenThrowNoToken(){
        // Arrange
        principalActual.setId("0");
        principalActual.setUsername("test");
        principalActual.setRole("tester");

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> tokenService.noTokenThrow(null));
        String expectedMessage = "No authorization found";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.equals(expectedMessage));
    }

    @Test
    void noTokenThrowNoUserInDatabase(){
        // Arrange
        principalActual.setId("0");
        principalActual.setUsername("test");
        principalActual.setRole("tester");

        String token = tokenService.generateToken(principalActual);

        Mockito.when(userService.getUserByUsername(principalActual.getUsername())).thenReturn(null);

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> tokenService.noTokenThrow(token));
        String expectedMessage = "Invalid user token";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.equals(expectedMessage));
    }
    //endregion
}