package com.example.taskmanagersystemapi.controller;

import com.example.taskmanagersystemapi.dto.TaskAndCommentsViewDto;
import com.example.taskmanagersystemapi.dto.TaskDto;
import com.example.taskmanagersystemapi.security.userDetails.JwtUser;
import com.example.taskmanagersystemapi.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @Operation(
            description = "Endpoint for get task list",
            summary = "Request for get tasks",
            parameters = {
                    @Parameter(name = "page", description = "page for pagination", example = "1"),
                    @Parameter(name = "size", description = "size tasks on 1 page", example = "25")
            },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Forbidden, if user not auth",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<TaskAndCommentsViewDto>> get(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {

        return ResponseEntity.ok(taskService.get(page, size));
    }


    @Operation(
            description = "Endpoint for get a filter tasks",
            summary = "Request for filter tasks by param",
            parameters = {
                    @Parameter(name = "param", description = "param for filter", example = "header"),
                    @Parameter(name = "value", description = "value for filter by param", example = "Task1"),
                    @Parameter(name = "page", description = "page for pagination", example = "1"),
                    @Parameter(name = "size", description = "size tasks on 1 page", example = "25")
            },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Forbidden, if user not auth",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/filter")
    public ResponseEntity<List<TaskAndCommentsViewDto>> filterBy(
            @RequestParam("param") String param,
            @RequestParam("value") String value,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {

        return ResponseEntity.ok(taskService.filterBy(param, value, page, size));
    }

    @Operation(
            description = "Endpoint for get task by id",
            summary = "Request for get task by id",
            parameters = {
                    @Parameter(name = "id", description = "Id of Task", example = "2"),
            },
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Forbidden, if user not auth",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<TaskAndCommentsViewDto> getById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(taskService.getById(id));
    }

    @Operation(
            description = "Endpoint for save a new Task",
            summary = "Request for create a new Task",
            responses = {
                    @ApiResponse(
                            description = "Success if body is valid and user auth",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad request, if task is not a valid",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Forbidden, if user not auth",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody TaskDto taskDto, @AuthenticationPrincipal JwtUser jwtUser) {
        taskService.create(taskDto, jwtUser);

        return ResponseEntity.ok().build();
    }

    @Operation(
            description = "Endpoint for update Task",
            summary = "Request for update Task",
            parameters = {
                    @Parameter(name = "id", description = "Id of Task", example = "2"),
            },
            responses = {
                    @ApiResponse(
                            description = "Success if body of task is valid and user auth",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Bad request, if task is not a valid",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Forbidden, if user not auth or user not author of task",
                            responseCode = "403"
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto, @AuthenticationPrincipal JwtUser jwtUser) {
        taskService.update(id, taskDto, jwtUser);

        return ResponseEntity.ok().build();
    }

    @Operation(
            description = "Endpoint for change status of Task",
            summary = "Request for change status of Task",
            parameters = {
                    @Parameter(name = "id", description = "Id of Task", example = "2"),
                    @Parameter(name = "status", description = "Status to change", example = "IN_PROGRESS")
            },
            responses = {
                    @ApiResponse(
                            description = "Success if status is valid and user auth",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Forbidden, if user not auth, user is not author or executor of task",
                            responseCode = "403"
                    )
            }
    )
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestParam("status") String status, @AuthenticationPrincipal JwtUser jwtUser) {
        taskService.changeStatus(id, status, jwtUser);

        return ResponseEntity.ok().build();
    }

    @Operation(
            description = "Endpoint for appoint an executor of Task",
            summary = "Request for appoint an executor of Task",
            parameters = {
                    @Parameter(name = "id", description = "Id of Task", example = "2"),
                    @Parameter(name = "executorId", description = "User id", example = "5")
            },
            responses = {
                    @ApiResponse(
                            description = "Success user auth and author",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Forbidden, if user not auth, user is not author of task",
                            responseCode = "403"
                    )
            }
    )
    @PatchMapping("/{id}/executor")
    public ResponseEntity<Void> appointExecutor(@PathVariable Long id, @RequestParam("executorId") Long executorId, @AuthenticationPrincipal JwtUser jwtUser) {
        taskService.appointExecutor(id, executorId, jwtUser);

        return ResponseEntity.ok().build();
    }

    @Operation(
            description = "Endpoint for remove Task",
            summary = "Request for remove Task",
            parameters = {
                    @Parameter(name = "id", description = "Id of Task", example = "2"),
            },
            responses = {
                    @ApiResponse(
                            description = "Success user auth and author",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Forbidden, if user not auth, user is not author of task",
                            responseCode = "403"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id, @AuthenticationPrincipal JwtUser jwtUser) {
        taskService.remove(id, jwtUser);

        return ResponseEntity.ok().build();
    }
}
