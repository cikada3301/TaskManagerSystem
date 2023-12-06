package com.example.taskmanagersystemapi.controller;

import com.example.taskmanagersystemapi.dto.CommentCreationDto;
import com.example.taskmanagersystemapi.security.userDetails.JwtUser;
import com.example.taskmanagersystemapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(
            description = "Endpoint for save a comment to task",
            summary = "Request for save a comment",
            parameters = {
                    @Parameter(name = "taskId", example = "3")
            },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad request, if email, username password are not valid",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Forbidden, if user not auth",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping("/{taskId}")
    public ResponseEntity<Void> create(@PathVariable Long taskId, @Valid @RequestBody CommentCreationDto commentCreationDto, @AuthenticationPrincipal JwtUser jwtUser) {
        commentService.create(taskId, commentCreationDto, jwtUser);

        return ResponseEntity.ok().build();
    }
}
