package com.revature.trailmates.user;

import com.revature.trailmates.auth.AuthService;
import com.revature.trailmates.user.dtos.requests.EditUserRequest;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;


//This is the class used for interacting with users.
@Service
@Transactional
public class UserService {

    @Inject
    private final UserRepository userRepository;


    @Inject
    @Autowired
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }


//region Query
    public User getUserById(String id) { return userRepository.getUserByID(id); }
    public User getUserByUsername(String username){
        return userRepository.getUserByUsername(username);
    }
    public User getUserByUsernameAndPassword(String username, String password) { return userRepository.getUserByUsernameAndPassword(username, password); }
    public ArrayList<User> getAllUsers() { return userRepository.getAllUsers(); }
//endregion


    public User UpdateUser(String tokenId, EditUserRequest request){
        User currentUser = userRepository.getUserByID(tokenId);

        boolean isPasswordChanged = false;

        if (!request.getEmail().equals("")) {
            if (new AuthService(userRepository).isValidEmail(request.getEmail()))
                currentUser.setEmail(request.getEmail());
            else{ // Email invalid format
                throw new InvalidRequestException("Email invalid!"); //Change to 406 later.
            }
        }

//        if (!request.getPassword().equals("")){
//            isPasswordChanged = true;
//            currentUser.setPassword(request.getPassword());
//        }

        if (!request.getBio().equals("") ) {
            if (request.getBio().length() < 255) currentUser.setBio(request.getBio());
            else throw new InvalidRequestException("Bio must be less than 255 characters!"); //Change to 406 later
        }

        if (request.getAge() > 13 || request.getAge() == 0) currentUser.setAge(request.getAge());
        else if (request.getAge() <= 13) throw new InvalidRequestException("Users must be older than 13 to use our services for Child Protection."); //Change to 406 later

//        if (!request.getRole_id().equals("")){
//            boolean roleExists = new UserRolesService(new UserRolesDAO()).getExistsInColumnByStringValue("id", request.getRole_id());
//            if (roleExists == false)
//                throw new InvalidRequestException("Role type doesn't exist! Canceled modification.");
//            else
//                currentUser.setRole_id(request.getRole_id());
//        }

        //currentUser.setIs_active(request.isIs_active());
        userRepository.updateUser(currentUser.getEmail(), currentUser.getBio(), currentUser.getAge(), currentUser.getId());//, isPasswordChanged);

        return currentUser;
    }


}
