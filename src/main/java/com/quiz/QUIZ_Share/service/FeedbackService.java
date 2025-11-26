package com.quiz.QUIZ_Share.service;

import com.quiz.QUIZ_Share.dto.feedback.FeedbackCreatRequest;
import com.quiz.QUIZ_Share.dto.feedback.FeedbackResponse;
import com.quiz.QUIZ_Share.dto.quiz.QuizUpdateRequest;
import com.quiz.QUIZ_Share.dto.takenQuiz.TakenQuizResponse;
import com.quiz.QUIZ_Share.entity.Feedback;
import com.quiz.QUIZ_Share.entity.Quiz;
import com.quiz.QUIZ_Share.mappers.FeedbackMapper;
import com.quiz.QUIZ_Share.repositories.FeedbackRepository;
import com.quiz.QUIZ_Share.repositories.QuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final QuizService quizService;
    private final QuizRepository quizRepository;

    public FeedbackResponse getFeedback(Integer authorId, Integer quizId) {
        Feedback feedback = feedbackRepository.findByAuthorIdAndQuizId(authorId, quizId);
        if (feedback == null) {
            return new FeedbackResponse();
        }

        return feedbackMapper.toDto(feedback);
    }

    public FeedbackResponse createFeedback(FeedbackCreatRequest feedback) {

        Quiz quiz = quizRepository.findById(feedback.getQuizId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Quiz not found by this id: %d", feedback.getQuizId())));

        log.info(feedback.toString());
        Feedback newFeedback = new Feedback();
        newFeedback.setFeedback(feedback.getFeedback());
        newFeedback.setStar(feedback.getStar());
        newFeedback.setAuthorId(feedback.getAuthorId());
        newFeedback.setQuizId(feedback.getQuizId());
        newFeedback.setToUserId(Long.valueOf(quiz.getUserId()));

        QuizUpdateRequest quizUpdateRequest = new QuizUpdateRequest();
        quizUpdateRequest.setAuthorId(Long.valueOf(quiz.getUserId()));
        quizUpdateRequest.setRate(feedback.getStar());

        newFeedback = feedbackRepository.save(newFeedback);
        quizService.updateQuiz(feedback.getQuizId(), quizUpdateRequest);

        return feedbackMapper.toDto(newFeedback);
    }

    public List<FeedbackResponse> getByQuizId(Long id) {
        List<Feedback> feedbacks = feedbackRepository.findAllByQuizId(id);

        return feedbacks.stream().map(feedbackMapper::toDto).toList();
    }
}
