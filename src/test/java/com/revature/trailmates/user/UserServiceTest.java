package com.revature.trailmates.user;

import com.revature.trailmates.auth.AuthService;
import com.revature.trailmates.auth.dtos.requests.LoginRequest;
import com.revature.trailmates.auth.dtos.requests.NewUserRequest;
import com.revature.trailmates.user.dtos.requests.EditUserRequest;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Spy
    EditUserRequest editUserRequest;

    @Test
    void getUserByUsername() {
    }

    @Test
    void updateUser() {
        //editUserRequest = new EditUserRequest();


//        Mockito.when(userRepository.getUserByUsername(loginRequest.getUsername())).thenReturn(dummy);
//        assertEquals(dummy, authService.login(loginRequest));

        //User dummy = new User();

        //Mockito.when(userService.UpdateUser(anyString(), any())).thenReturn(dummy);
        //assertEquals(dummy, authService.login(loginRequest));

        //region email invalid
        {
            editUserRequest.setId("0");
            editUserRequest.setUsername("foo");
            editUserRequest.setPassword("123");
            editUserRequest.setEmail("email");
            editUserRequest.setAge(20);
            editUserRequest.setBio("I'm the guy");
            editUserRequest.setRole("DEFAULT");

            Exception exception = assertThrows(RuntimeException.class, () -> userService.UpdateUser("0", editUserRequest));
            String expectedMessage = "Email invalid!";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }

        //endregion


        //region underage
        {
            editUserRequest.setId("0");
            editUserRequest.setUsername("foo");
            editUserRequest.setPassword("123");
            editUserRequest.setEmail("email@email.net");
            editUserRequest.setAge(5);
            editUserRequest.setBio("I'm the guy");
            editUserRequest.setRole("DEFAULT");

            Exception exception = assertThrows(RuntimeException.class, () -> userService.UpdateUser("0", editUserRequest));
            String expectedMessage = "Users must be older than 13 to use our services for Child Protection.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }
        //endregion

    }
    //Email invalid!
/*


    @Test
    void register() {
        newUserRequest.setUsername("test");

        Exception exception = assertThrows(RuntimeException.class, () -> authService.register(newUserRequest));
        String expectedMessage = " is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void isValidEmail() {

    }
 */

}