package com.quiz.QUIZ_Share.mappers;

import com.quiz.QUIZ_Share.dto.auth.UserResponse;
import com.quiz.QUIZ_Share.entity.User;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = QuestionMapper.class
)
public interface UserMapper {

    UserResponse toDto(User user);
}
