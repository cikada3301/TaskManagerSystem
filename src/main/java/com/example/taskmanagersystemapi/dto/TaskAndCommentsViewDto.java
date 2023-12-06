package com.example.taskmanagersystemapi.dto;

import com.example.taskmanagersystemapi.model.User;
import com.example.taskmanagersystemapi.model.enums.Priority;
import com.example.taskmanagersystemapi.model.enums.Status;
import lombok.Data;

import java.util.List;

@Data
public class TaskAndCommentsViewDto {
    private Long id;
    private String header;
    private String description;
    private Status status;
    private Priority priority;
    private User author;
    private User executor;
    private List<CommentViewDto> comments;
}
