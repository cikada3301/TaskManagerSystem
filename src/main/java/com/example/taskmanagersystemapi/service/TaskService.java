package com.example.taskmanagersystemapi.service;

import com.example.taskmanagersystemapi.dto.TaskAndCommentsViewDto;
import com.example.taskmanagersystemapi.dto.TaskDto;
import com.example.taskmanagersystemapi.security.userDetails.JwtUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskService {

    List<TaskAndCommentsViewDto> get(Integer page, Integer size);
    TaskAndCommentsViewDto getById(Long id);
    List<TaskAndCommentsViewDto> filterBy(String param, String value, Integer page, Integer size);
    void create(TaskDto taskDto, JwtUser jwtUser);
    void update(Long id, TaskDto taskDto, JwtUser jwtUser);
    void changeStatus(Long id, String status, JwtUser jwtUser);
    void appointExecutor(Long id, Long executorId, JwtUser jwtUser);
    void remove(Long id, JwtUser jwtUser);
}
