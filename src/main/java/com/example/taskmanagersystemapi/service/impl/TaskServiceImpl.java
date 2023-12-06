package com.example.taskmanagersystemapi.service.impl;

import com.example.taskmanagersystemapi.dto.CommentViewDto;
import com.example.taskmanagersystemapi.dto.TaskAndCommentsViewDto;
import com.example.taskmanagersystemapi.dto.TaskDto;
import com.example.taskmanagersystemapi.exception.NotTaskOwnerException;
import com.example.taskmanagersystemapi.exception.TaskNotFoundException;
import com.example.taskmanagersystemapi.exception.UserNotFoundException;
import com.example.taskmanagersystemapi.mapper.CommentMapper;
import com.example.taskmanagersystemapi.mapper.TaskMapper;
import com.example.taskmanagersystemapi.model.Task;
import com.example.taskmanagersystemapi.model.User;
import com.example.taskmanagersystemapi.model.enums.Status;
import com.example.taskmanagersystemapi.repository.CommentRepository;
import com.example.taskmanagersystemapi.repository.TaskRepository;
import com.example.taskmanagersystemapi.repository.UserRepository;
import com.example.taskmanagersystemapi.security.userDetails.JwtUser;
import com.example.taskmanagersystemapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper = CommentMapper.INSTANCE;
    private final TaskMapper taskMapper = TaskMapper.INSTANCE;

    @Override
    @Transactional
    public List<TaskAndCommentsViewDto> get(Integer page, Integer size) {

        List<TaskAndCommentsViewDto> result = taskRepository.findAll()
                .stream()
                .map(taskMapper::taskToTaskAndCommentsViewDto)
                .toList();

        return getTaskAndCommentsViewDtos(page, size, result);
    }

    @Override
    @Transactional
    public TaskAndCommentsViewDto getById(Long id) {

        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("This task doesn't exist"));

        TaskAndCommentsViewDto taskAndCommentsViewDto = taskMapper.taskToTaskAndCommentsViewDto(task);

        List<CommentViewDto> commentViewDtoList = commentRepository.findAllByTaskId(task.getId())
                .stream()
                .map(commentMapper::commentToCommentViewDto)
                .toList();

        taskAndCommentsViewDto.setComments(commentViewDtoList);

        return taskAndCommentsViewDto;
    }

    @Override
    @Transactional
    public List<TaskAndCommentsViewDto> filterBy(String param, String value, Integer page, Integer size) {
        if (Objects.equals(param, "author") || Objects.equals(param, "executor")) {
            List<TaskAndCommentsViewDto> result = taskRepository.findAll()
                    .stream()
                    .map(taskMapper::taskToTaskAndCommentsViewDto)
                    .filter(t -> Objects.equals(t.getAuthor().getId(), Long.valueOf(value)) || Objects.equals(t.getExecutor().getId(), Long.valueOf(value)))
                    .toList();
            return getTaskAndCommentsViewDtos(page, size, result);
        }
        else {
            Specification<Task> specification = (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(param), value);

            List<TaskAndCommentsViewDto> result = taskRepository.findAll(specification)
                    .stream()
                    .map(taskMapper::taskToTaskAndCommentsViewDto)
                    .toList();

            return getTaskAndCommentsViewDtos(page, size, result);
        }
    }

    @Override
    @Transactional
    public void create(TaskDto taskDto, JwtUser jwtUser) {

        User user = userRepository.findByEmail(jwtUser.getUsername());

        Task task = taskMapper.taskDtoToTask(taskDto);
        task.setAuthor(user);

        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void update(Long id, TaskDto taskDto, JwtUser jwtUser) {

        Task taskOnUpdate = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("This task doesn't exist"));

        User user = userRepository.findByEmail(jwtUser.getUsername());

        if (!Objects.equals(taskOnUpdate.getAuthor().getId(), user.getId())) {
            throw new NotTaskOwnerException("You are not author");
        }

        taskOnUpdate = taskMapper.taskDtoToTask(taskDto);
        taskOnUpdate.setId(id);

        taskRepository.save(taskOnUpdate);
    }

    @Override
    @Transactional
    public void changeStatus(Long id, String status, JwtUser jwtUser) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("This task doesn't exist"));

        User user = userRepository.findByEmail(jwtUser.getUsername());

        if (!Objects.equals(task.getAuthor().getId(), user.getId()) || !Objects.equals(task.getExecutor().getId(), user.getId())) {
            throw new NotTaskOwnerException("You are not author");
        }

        task.setStatus(Status.valueOf(status));

        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void appointExecutor(Long id, Long executorId, JwtUser jwtUser) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("This task doesn't exist"));

        User user = userRepository.findByEmail(jwtUser.getUsername());

        if (!Objects.equals(task.getAuthor().getId(), user.getId())) {
            throw new NotTaskOwnerException("You are not author");
        }

        task.setExecutor(userRepository.findById(executorId).orElseThrow(() -> new UserNotFoundException("Executor doesn't exist")));

        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void remove(Long id, JwtUser jwtUser) {

        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("This task doesn't exist"));

        User user = userRepository.findByEmail(jwtUser.getUsername());

        if (!Objects.equals(task.getAuthor().getId(), user.getId())) {
            throw new NotTaskOwnerException("You are not author");
        }

        taskRepository.deleteById(id);
    }

    private List<TaskAndCommentsViewDto> getTaskAndCommentsViewDtos(Integer page, Integer size, List<TaskAndCommentsViewDto> result) {
        for (TaskAndCommentsViewDto task : result) {
            List<CommentViewDto> commentViewDtoList = commentRepository.findAllByTaskId(task.getId())
                    .stream()
                    .map(commentMapper::commentToCommentViewDto)
                    .toList();

            task.setComments(commentViewDtoList);
        }

        int from = page * size - size;
        int to = from + size;

        return result.subList(from, Math.min(to, result.size()));
    }
}
