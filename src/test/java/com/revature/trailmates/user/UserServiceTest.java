package com.revature.trailmates.user;

import com.revature.trailmates.auth.AuthService;
import com.revature.trailmates.auth.dtos.requests.LoginRequest;
import com.revature.trailmates.auth.dtos.requests.NewUserRequest;
import com.revature.trailmates.user.dtos.requests.EditUserRequest;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Spy
    EditUserRequest editUserRequest;

    @Test
    void getUserByUsername() {
        User dummy = new User();
        dummy.setUsername("foobar");

        Mockito.when(userRepository.getUserByUsername(dummy.getUsername())).thenReturn(dummy);

        userRepository.getUserByUsername(dummy.getUsername());

        assertTrue("foobar".contains(dummy.getUsername()));
    }

    @Test
    void getUserById(){
        User dummy = new User();
        dummy.setId("foobar");

        Mockito.when(userRepository.getUserByID(dummy.getId())).thenReturn(dummy);

        userRepository.getUserByID(dummy.getId());

        assertTrue("foobar".contains(dummy.getId()));
    }

    @Test
    void getAllUsers(){
        ArrayList<User> users = new ArrayList<User>();

        User myUser = new User();

        myUser.setId("0");
        myUser.setUsername("foo");
        myUser.setPassword("123");
        myUser.setEmail("email");
        myUser.setAge(20);
        myUser.setBio("I'm the guy");
        myUser.setRole("DEFAULT");

        users.add(myUser);


        Mockito.when(userRepository.getAllUsers()).thenReturn(users);

        ArrayList<User> usersToCheck = userRepository.getAllUsers();

        //assertTrue("foobar".contains(dummy.getId()));
        assertEquals(users.get(0).getId(), usersToCheck.get(0).getId());

    }

    @Test
    void updateUser() {
        editUserRequest = new EditUserRequest();
        editUserRequest.setId("0");

        User dummy = new User();
        dummy.setId("0");

        Mockito.when(userRepository.getUserByID(editUserRequest.getId())).thenReturn(dummy);

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

        //region too long bio
        {
            editUserRequest.setId("0");
            editUserRequest.setUsername("foo");
            editUserRequest.setPassword("123");
            editUserRequest.setEmail("email@email.net");
            editUserRequest.setAge(20);
            editUserRequest.setBio("0123456789 0123456789 0123456789 0123456789 0123456789  0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789");
            editUserRequest.setRole("DEFAULT");

            Exception exception = assertThrows(RuntimeException.class, () -> userService.UpdateUser("0", editUserRequest));
            String expectedMessage = "Bio must be less than 255 characters!";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }
        //endregion

    }



}