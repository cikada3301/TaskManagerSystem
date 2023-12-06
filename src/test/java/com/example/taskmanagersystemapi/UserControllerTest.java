package com.example.taskmanagersystemapi;

import com.example.taskmanagersystemapi.controller.UserController;
import com.example.taskmanagersystemapi.dto.CommentCreationDto;
import com.example.taskmanagersystemapi.dto.UserRegistrationDto;
import com.example.taskmanagersystemapi.model.User;
import com.example.taskmanagersystemapi.security.userDetails.JwtUser;
import com.example.taskmanagersystemapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    public void testCreateTaskOnSuccess() {

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();

        ResponseEntity<String> response = userController.register(userRegistrationDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(userService).register(userRegistrationDto);
    }
}
