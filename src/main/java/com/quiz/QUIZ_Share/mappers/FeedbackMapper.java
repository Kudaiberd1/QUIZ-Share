package com.quiz.QUIZ_Share.mappers;


import com.quiz.QUIZ_Share.dto.feedback.FeedbackResponse;
import com.quiz.QUIZ_Share.entity.Feedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    FeedbackResponse toDto(Feedback feedback);
}
