package com.example.taskmanagersystemapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDto {
    @NotBlank(message = "Please fill out the required username")
    @Size(min = 4, message = "Please make sure you are using a valid username")
    private String username;

    @Email
    @NotBlank(message = "Please fill out the required email")
    private String email;

    @Size(min = 8, message = "Please make sure you are using a valid password")
    @NotBlank(message = "Please fill out the required email")
    private String password;
}
