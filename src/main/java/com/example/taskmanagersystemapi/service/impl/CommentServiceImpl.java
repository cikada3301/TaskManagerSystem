package com.example.taskmanagersystemapi.service.impl;

import com.example.taskmanagersystemapi.dto.CommentCreationDto;
import com.example.taskmanagersystemapi.exception.TaskNotFoundException;
import com.example.taskmanagersystemapi.mapper.CommentMapper;
import com.example.taskmanagersystemapi.model.Comment;
import com.example.taskmanagersystemapi.repository.CommentRepository;
import com.example.taskmanagersystemapi.repository.TaskRepository;
import com.example.taskmanagersystemapi.repository.UserRepository;
import com.example.taskmanagersystemapi.security.userDetails.JwtUser;
import com.example.taskmanagersystemapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper = CommentMapper.INSTANCE;

    @Override
    public void create(Long taskId, CommentCreationDto commentCreationDto, JwtUser jwtUser) {
        Comment comment = commentMapper.commentCreationDtoToComment(commentCreationDto);
        comment.setAuthor(userRepository.findByEmail(jwtUser.getUsername()));
        comment.setTask(taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("This task doesn't exist")));

        commentRepository.save(comment);
    }
}
