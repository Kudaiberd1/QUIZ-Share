package com.quiz.quizshare.service;

import com.quiz.quizshare.dto.feedback.FeedbackResponse;
import com.quiz.quizshare.entity.Feedback;
import com.quiz.quizshare.entity.User;
import com.quiz.quizshare.mappers.FeedbackMapper;
import com.quiz.quizshare.repositories.FeedbackRepository;
import com.quiz.quizshare.repositories.UserRepository;
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

    public List<FeedbackResponse> getFeedbacks(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Authorization not found"));

        List<Feedback> feedbacks = feedbackRepository.findAllByToUserId(
                Long.valueOf(user.getId()),
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        log.info("feedbacks size: {}", feedbacks);

        return feedbacks.stream().map(feedbackMapper::toDto).toList();
    }

    public void markAsRead(Integer id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Feedback not found!"));

        feedback.setIsReaded(true);
        feedbackRepository.save(feedback);

    }
}
