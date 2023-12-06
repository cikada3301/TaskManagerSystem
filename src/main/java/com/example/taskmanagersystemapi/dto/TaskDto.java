package com.example.taskmanagersystemapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskDto {
    @NotBlank(message = "Please fill out the required header")
    private String header;

    @NotBlank(message = "Please fill out the required description")
    private String description;

    @NotBlank(message = "Please fill out the required status")
    private String status;

    @NotBlank(message = "Please fill out the required priority")
    private String priority;
}
