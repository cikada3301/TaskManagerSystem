package com.example.taskmanagersystemapi.mapper;

import com.example.taskmanagersystemapi.dto.CommentCreationDto;
import com.example.taskmanagersystemapi.dto.CommentViewDto;
import com.example.taskmanagersystemapi.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment commentCreationDtoToComment(CommentCreationDto commentCreationDto);
    CommentViewDto commentToCommentViewDto(Comment comment);
}
