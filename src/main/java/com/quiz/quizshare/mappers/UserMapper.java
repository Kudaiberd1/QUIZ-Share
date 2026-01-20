package com.quiz.quizshare.mappers;

import com.quiz.quizshare.dto.auth.UserResponse;
import com.quiz.quizshare.entity.User;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = QuestionMapper.class
)
public interface UserMapper {

    UserResponse toDto(User user);
}
