package com.example.taskmanagersystemapi.mapper;

import com.example.taskmanagersystemapi.dto.TaskAndCommentsViewDto;
import com.example.taskmanagersystemapi.dto.TaskDto;
import com.example.taskmanagersystemapi.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task taskDtoToTask(TaskDto taskDto);
    TaskAndCommentsViewDto taskToTaskAndCommentsViewDto(Task task);
}
