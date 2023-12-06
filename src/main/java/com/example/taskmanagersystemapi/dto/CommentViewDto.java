package com.example.taskmanagersystemapi.dto;

import com.example.taskmanagersystemapi.model.User;
import lombok.Data;

@Data
public class CommentViewDto {
    private String text;
    private User author;
}
