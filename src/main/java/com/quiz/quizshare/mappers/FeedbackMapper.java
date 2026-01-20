package com.quiz.quizshare.mappers;


import com.quiz.quizshare.dto.feedback.FeedbackResponse;
import com.quiz.quizshare.entity.Feedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    FeedbackResponse toDto(Feedback feedback);
}
