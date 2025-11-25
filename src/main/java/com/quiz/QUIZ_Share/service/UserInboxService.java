package com.quiz.QUIZ_Share.service;

import com.quiz.QUIZ_Share.dto.feedback.FeedbackResponse;
import com.quiz.QUIZ_Share.entity.Feedback;
import com.quiz.QUIZ_Share.entity.User;
import com.quiz.QUIZ_Share.mappers.FeedbackMapper;
import com.quiz.QUIZ_Share.repositories.FeedbackRepository;
import com.quiz.QUIZ_Share.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInboxService {

    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    public List<FeedbackResponse> getFeedbacks(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Authorization not found"));

        List<Feedback> feedbacks = feedbackRepository.findAllByAuthorId(Long.valueOf(user.getId()), Sort.by(Sort.Direction.DESC, "created_at"));

        return feedbacks.stream().map(feedbackMapper::toDto).toList();
    }

    public void markAsRead(Integer id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Feedback not found!"));

        feedback.setIsReaded(true);
        feedbackRepository.save(feedback);

    }
}
