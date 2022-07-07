package com.revature.trailmates.auth;

import com.revature.trailmates.auth.dtos.requests.LoginRequest;
import com.revature.trailmates.auth.dtos.requests.NewUserRequest;
import com.revature.trailmates.user.User;
import com.revature.trailmates.user.UserRepository;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.AuthenticationException;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.ResourceConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class AuthService {

    @Inject
    private final UserRepository userRepository;

    @Inject
    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(LoginRequest loginRequest){
        if(loginRequest.getUsername() == null || loginRequest.getPassword() == null) throw new InvalidRequestException();//401
        //todo talk with alec about user input validation (username, password, and other things)
        //if(!isValidUsername(loginRequest.getUsername()) || !isValidPassword(loginRequest.getPassword())) throw new InvalidRequestException("Invalid username or password");//404
        User user = userRepository.getUserByUsername(loginRequest.getUsername());
        if(user == null)throw new InvalidRequestException("Invalid credentials");//404
        //if (!user.getIsActive()) throw new AuthenticationException("Inactive User");//403
        return user;
    }

    public User register(NewUserRequest request){
        User user = request.extractUser();

        //validation checks
        String message = nullChecker(request);
        if(!message.isEmpty()) throw new InvalidRequestException(message);
        if(userExists(user.getUsername())) throw new ResourceConflictException("This username is already taken");
        //if(!isValidUsername(user.getUsername())) throw new InvalidRequestException("Invalid username, must be 8-20 characters long and no special characters except _ and .");
        //if(!isValidPassword(user.getPassword())) throw new InvalidRequestException("Invalid password, must be longer than 8 characters and contain one number, one special character, and one alphabetical character");
        if(!isValidEmail(user.getEmail())) throw new InvalidRequestException("Invalid email, must be a valid email address");

        userRepository.save(user);
        //todo talk with alec about password encryption
        //userRepository.encryptPassword(user.getPassword(),user.getId());

        return user;
    }

    private boolean userExists(String username){
        return userRepository.getUserByUsername(username) != null;
    }

    private String nullChecker(NewUserRequest request){
        String eMessage = "";
        //Checks if any fields are null and builds a message accordingly
        try {
            Field[] fields = com.revature.trailmates.auth.dtos.requests.NewUserRequest.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(request) == null) {
                    if(!eMessage.isEmpty()){
                        eMessage += ", ";
                    }
                    eMessage += field.getName() + " is null";
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return eMessage;
    }

    public boolean isValidEmail(String email){
        return email.matches("^([\\w][\\-\\_\\.]?)*\\w@([\\w+]\\-?)*\\w\\.\\w+$");
    }


    /*private String nullChecker(NewUserRequest request){
        String eMessage = "";
        //Checks if any fields are null and builds a message accordingly
        //todo talk with team about using bean utils
        try {
            Map<String, String> nullFields = BeanUtils.describe(request);
            for (Map.Entry<String, String> key : nullFields.entrySet()) {
                if (key.getValue() == null) {
                    if(!eMessage.isEmpty()){
                        eMessage += ", ";
                    }
                    eMessage += key.getKey() + " is null";
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return eMessage;
    }*/

}
