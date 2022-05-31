package controller;

import favoriteSongs.controller.UserController;
import favoriteSongs.dto.UserDTO;
import favoriteSongs.entity.Users;
import favoriteSongs.service.UserService;
import favoriteSongs.service.UserValidatorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserValidatorService userValidatorService;

    @Test
    public void testRegisterUserShouldReturnCreateHttpStatusCodeWithUsername() {
        UserDTO userDTO = new UserDTO("demoName", "demoLastName", "demo@test.com", "demoPass");
        Users user = new Users("demoName", "demoLastName", "demo@test.com", "demoPass");
        when(userValidatorService.validateIfUserIsNew(userDTO)).thenReturn(true);
        when(userService.mapUserFromUserDTO(userDTO)).thenReturn(user);
        when(userService.registerUser(user)).thenReturn(user);
        ResponseEntity<String> responseEntity = userController.registerUser(userDTO);
        Assert.assertEquals(201, responseEntity.getStatusCodeValue());
        Assert.assertEquals("user created with id as same as your emailId: demo@test.com", responseEntity.getBody());
    }

    @Test
    public void testRegisterUserShouldReturnBadRequestHttpStatusCodeWithErrorMessage() {
        UserDTO userDTO = new UserDTO("demoName", "demoLastName", "demo@test.com", "demoPass");
        when(userValidatorService.validateIfUserIsNew(userDTO)).thenReturn(false);
        ResponseEntity<String> responseEntity = userController.registerUser(userDTO);
        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        Assert.assertEquals("Sorry, duplicate registration not allowed", responseEntity.getBody());
    }

    @Test
    public void testLoginUserShouldReturnOKHttpStatusCodeAndUserDTO() {
        Users user = new Users("demoName", "demoLastName", "demo@test.com", "demoPass");
        UserDTO userDTO = new UserDTO("demoName", "demoLastName", "demo@test.com", "demoPass");
        when(userService.getLoggedInUserDetails()).thenReturn(user);
        when(userService.mapUserDTOFromUsers(user)).thenReturn(userDTO);
        ResponseEntity<UserDTO> responseEntity = userController.loginUser();
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals("demoName", responseEntity.getBody().getFirstName());
    }

    @Test
    public void testDeleteUserShouldReturnOKHttpStatusCodeAndValidMessage() {
        Users user = new Users("demoName", "demoLastName", "demo@test.com", "demoPass");
        when(userService.getLoggedInUserDetails()).thenReturn(user);
        doNothing().when(userService).deleteUser(user);
        ResponseEntity<String> responseEntity = userController.deleteUser();
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals("User entry deleted with username: demo@test.com", responseEntity.getBody());
    }


}
