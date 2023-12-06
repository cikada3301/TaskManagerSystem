package com.example.taskmanagersystemapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreationDto {
    @NotBlank(message = "Please fill out the required text")
    private String text;
}
