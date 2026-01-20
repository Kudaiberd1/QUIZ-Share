package com.quiz.quizshare.mappers;

import com.quiz.quizshare.dto.quiz.QuizResponse;
import com.quiz.quizshare.entity.Quiz;
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
