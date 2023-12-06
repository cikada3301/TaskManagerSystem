package com.example.taskmanagersystemapi;

import com.example.taskmanagersystemapi.controller.CommentController;
import com.example.taskmanagersystemapi.dto.CommentCreationDto;
import com.example.taskmanagersystemapi.model.User;
import com.example.taskmanagersystemapi.security.userDetails.JwtUser;
import com.example.taskmanagersystemapi.service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CommentControllerTests {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @Mock
    private JwtUser jwtUser = new JwtUser(new User(5L, "qwerty12345", "qwerty12345@l.ru", "12345qwerty"));


    @Test
    public void testCreateTaskOnSuccess() {

        CommentCreationDto comment = new CommentCreationDto();
        comment.setText("zxc");

        ResponseEntity<Void> response = commentController.create(1L, comment, jwtUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(commentService).create(1L, comment, jwtUser);
    }
}
