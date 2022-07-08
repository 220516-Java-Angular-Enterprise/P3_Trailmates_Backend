package com.revature.trailmates.auth;

import com.revature.trailmates.auth.dtos.requests.LoginRequest;
import com.revature.trailmates.auth.dtos.requests.NewUserRequest;
import com.revature.trailmates.user.User;
import com.revature.trailmates.user.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Spy
    private LoginRequest loginRequest;

    @Spy
    private NewUserRequest newUserRequest;

    @Test
    void loginSuccess() {
        // Arrange
        loginRequest.setUsername("testUser001");
        loginRequest.setPassword("P@ssw0rd");

        User dummy = new User();
        dummy.setUsername("testUser001");

        Mockito.when(userRepository.getUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(dummy);

        // Act
        User test = authService.login(loginRequest);

        // Assert
        assertEquals(dummy, test);
    }

    //region Register Tests
    @Test
    void invalidUsername(){
        // Arrange
        newUserRequest.setUsername("test");
        newUserRequest.setPassword("P@ssw0rd");
        newUserRequest.setEmail("test@testmail.com");
        newUserRequest.setBio("Test user");
        newUserRequest.setAge(20);

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> authService.register(newUserRequest));
        String expectedMessage = "Invalid username, must be 8-20 characters long and no special characters except _ and .";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.equals(expectedMessage));
    }

    @Test
    void invalidPassword(){
        // Arrange
        newUserRequest.setUsername("testUser001");
        newUserRequest.setPassword("password");
        newUserRequest.setEmail("test@testmail.com");
        newUserRequest.setBio("Test user");
        newUserRequest.setAge(20);


        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> authService.register(newUserRequest));
        String expectedMessage = "Invalid password, must be longer than 8 characters and contain one number, one special character, and one alphabetical character";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void invalidEmail(){
        // Arrange
        newUserRequest.setUsername("testUser001");
        newUserRequest.setPassword("P@ssw0rd");
        newUserRequest.setEmail("email");
        newUserRequest.setBio("Test user");
        newUserRequest.setAge(20);

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> authService.register(newUserRequest));
        String expectedMessage = "Invalid email, must be a valid email address";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.equals(expectedMessage));
    }

    @Test
    void usernameAlreadyExists(){
        // Arrange
        newUserRequest.setUsername("testUser001");
        newUserRequest.setPassword("P@ssw0rd");
        newUserRequest.setEmail("test@testmail.com");
        newUserRequest.setBio("Test user");
        newUserRequest.setAge(20);

        User dummy = new User();
        dummy.setUsername("testUser001");

        Mockito.when(userRepository.getUserByUsername(newUserRequest.getUsername())).thenReturn(dummy);

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> authService.register(newUserRequest));
        String expectedMessage = "This username is already taken";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.equals(expectedMessage));
    }

    @Test
    void newUserRequestHasNullFields() {
        // Arrange
        newUserRequest.setUsername("test");

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> authService.register(newUserRequest));
        String expectedMessage = " is null";
        String actualMessage = exception.getMessage();

        // Assert
        assertTrue(actualMessage.contains(expectedMessage));
    }
    //endregion



}