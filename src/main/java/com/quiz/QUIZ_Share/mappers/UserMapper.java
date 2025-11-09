package com.quiz.QUIZ_Share.mappers;

import com.quiz.QUIZ_Share.dto.auth.UserReponse;
import com.quiz.QUIZ_Share.entity.User;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = QuestionMapper.class
)
public interface UserMapper {

    UserReponse toDto(User user);
}
