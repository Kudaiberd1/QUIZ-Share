package com.quiz.QUIZ_Share.mappers;

import com.quiz.QUIZ_Share.dto.takenQuiz.TakenQuizResponse;
import com.quiz.QUIZ_Share.entity.TakenQuiz;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TakenQuizMapper {
    TakenQuizResponse toDto(TakenQuiz takenQuiz);
}
