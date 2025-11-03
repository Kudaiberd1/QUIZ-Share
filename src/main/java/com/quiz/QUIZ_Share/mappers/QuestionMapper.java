package com.quiz.QUIZ_Share.mappers;

import com.quiz.QUIZ_Share.dto.question.QuestionResponse;
import com.quiz.QUIZ_Share.entity.Questions;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface QuestionMapper {
    QuestionResponse toDto(Questions question);
}