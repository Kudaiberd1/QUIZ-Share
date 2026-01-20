package com.quiz.QUIZ_Share.mappers;

import com.quiz.QUIZ_Share.dto.quiz.QuizResponse;
import com.quiz.QUIZ_Share.entity.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    uses = QuestionMapper.class,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface QuizMapper {

    QuizResponse toDto(Quiz quiz);
}
