package com.quiz.quizshare.mappers;

import com.quiz.quizshare.dto.question.QuestionResponse;
import com.quiz.quizshare.entity.Questions;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface QuestionMapper {
    QuestionResponse toDto(Questions question);
}