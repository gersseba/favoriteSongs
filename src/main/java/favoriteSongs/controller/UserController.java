package favoriteSongs.controller;

import favoriteSongs.dto.UserDTO;
import favoriteSongs.entity.Users;
import favoriteSongs.service.UserService;
import favoriteSongs.service.UserValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidatorService userValidatorService;

    @PostMapping("/register")
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

    @DeleteMapping("/operations/delete")
    public ResponseEntity<String> deleteUser() {
        Users loggedInUser = userService.getLoggedInUserDetails();
        String loggedInUserName = loggedInUser.getUsername();
        userService.deleteUser(loggedInUser);
        return ResponseEntity.status(200).body("User entry deleted with username: "+loggedInUserName);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
