package com.example.taskmanagersystemapi.service;

import com.example.taskmanagersystemapi.dto.CommentCreationDto;
import com.example.taskmanagersystemapi.security.userDetails.JwtUser;

public interface CommentService {
    void create(Long taskId, CommentCreationDto commentCreationDto, JwtUser jwtUser);
}
