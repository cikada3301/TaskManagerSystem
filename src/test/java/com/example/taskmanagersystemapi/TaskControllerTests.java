package com.example.taskmanagersystemapi;

import com.example.taskmanagersystemapi.controller.TaskController;
import com.example.taskmanagersystemapi.dto.TaskAndCommentsViewDto;
import com.example.taskmanagersystemapi.dto.TaskDto;
import com.example.taskmanagersystemapi.model.User;
import com.example.taskmanagersystemapi.model.enums.Priority;
import com.example.taskmanagersystemapi.model.enums.Status;
import com.example.taskmanagersystemapi.security.userDetails.JwtUser;
import com.example.taskmanagersystemapi.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskControllerTests {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @Mock
    private JwtUser jwtUser = new JwtUser(new User(5L, "qwerty12345", "qwerty12345@l.ru", "12345qwerty"));

    private TaskAndCommentsViewDto getMockTask() {

        TaskAndCommentsViewDto task = new TaskAndCommentsViewDto();
        task.setHeader("Zxc");
        task.setDescription("Zxczxczxc");
        task.setStatus(Status.COMPLETED);
        task.setPriority(Priority.LOW);

        return task;
    }

    @Test
    public void testGetTasksOnSuccess() {

        List<TaskAndCommentsViewDto> mockTasks = List.of(getMockTask());

        when(taskService.get(anyInt(), anyInt())).thenReturn(mockTasks);

        ResponseEntity<List<TaskAndCommentsViewDto>> response = taskController.get(1,10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockTasks, response.getBody());
    }

    @Test
    public void testGetByIdOnSuccess() {

        TaskAndCommentsViewDto mockTask = getMockTask();

        when(taskService.getById(anyLong())).thenReturn(mockTask);

        ResponseEntity<TaskAndCommentsViewDto> response = taskController.getById(1L);

        assertEquals(mockTask, response.getBody());
    }

    @Test
    public void testCreateTaskOnSuccess() {

        TaskDto task = new TaskDto();

        taskController.create(task, jwtUser);

        verify(taskService).create(task, jwtUser);
    }

    @Test
    public void testChangeStatusTaskOnSuccess() {

        Long id = 1L;
        String status = "IN_PROGRESS";

        taskController.changeStatus(id, status, jwtUser);

        verify(taskService).changeStatus(id, status, jwtUser);
    }

    @Test
    public void testUpdateTaskOnSuccess() {

        Long id = 1L;

        TaskDto dto = new TaskDto();

        taskController.update(id, dto, jwtUser);

        verify(taskService).update(id, dto, jwtUser);
    }

    @Test
    public void testDeleteTaskOnSuccess() {

        Long id = 1L;

        taskController.remove(id, jwtUser);

        verify(taskService).remove(id, jwtUser);
    }
}
