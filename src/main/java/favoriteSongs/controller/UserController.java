package favoriteSongs.controller;

import favoriteSongs.dto.UserDTO;
import favoriteSongs.entity.Users;
import favoriteSongs.service.UserService;
import favoriteSongs.service.UserValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidatorService userValidatorService;

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        boolean isUserNew = userValidatorService.validateIfUserIsNew(userDTO);
        if(isUserNew) {
            Users newUser = userService.mapUserFromUserDTO(userDTO);
            Users savedUser = userService.registerUser(newUser);
            return ResponseEntity.status(201).body("user created with id as same as your emailId: "+savedUser.getUsername());
        }
        else {
            return ResponseEntity.status(400).body("Sorry, duplicate registration not allowed");
        }
    }

    @PostMapping("/operations/login")
    public ResponseEntity<UserDTO> loginUser() {
       UserDTO userDTO = userService.mapUserDTOFromUsers(userService.getLoggedInUserDetails());
       return ResponseEntity.status(200).body(userDTO);
    }

    @DeleteMapping("/operations/deleteUser")
    public ResponseEntity<String> deleteUser() {
        Users loggedInUser = userService.getLoggedInUserDetails();
        String loggedInUserName = loggedInUser.getUsername();
        userService.deleteUser(loggedInUser);
        return ResponseEntity.status(200).body("User entry deleted with username: "+loggedInUserName);
    }

}
