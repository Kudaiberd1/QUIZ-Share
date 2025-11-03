package com.quiz.QUIZ_Share.service;

import com.quiz.QUIZ_Share.dto.QuizRequest;
import com.quiz.QUIZ_Share.dto.QuizResponse;
import com.quiz.QUIZ_Share.entity.Questions;
import com.quiz.QUIZ_Share.entity.Quiz;
import com.quiz.QUIZ_Share.entity.User;
import com.quiz.QUIZ_Share.mappers.QuizMapper;
import com.quiz.QUIZ_Share.repositories.QuizRepository;
import com.quiz.QUIZ_Share.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private final QuestionService questionService;
    private final UserRepository userRepository;

    public List<QuizResponse> getAll() {
        log.info("Get all quizzes");

        List<Quiz> quizzes = quizRepository.findAllWithQuestions();
        for (Quiz q : quizzes) {
            log.info("Quiz {} has {} questions", q.getTitle(), q.getQuestions().size());
        }
        return quizzes.stream().map(quizMapper::toDto).toList();
    }

    public QuizResponse getById(Long id) {
        log.info("Get quiz by id {}", id);
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        return quizMapper.toDto(quiz);
    }


    public QuizResponse createQuiz(@Valid QuizRequest quizRequest) {
        log.info("Create quiz {}", quizRequest);

        User user = userRepository.findById(quizRequest.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequest.getTitle());
        quiz.setDescription(quizRequest.getDescription());
        quiz.setSubject(quizRequest.getSubject());
        quiz.setDifficulty(quizRequest.getDifficulty());
        quiz.setPrivacy(quizRequest.getPrivacy());
        quiz.setUser(user);

        var savedQuiz = quizRepository.save(quiz);

        var questions = questionService.buildQuestion(quiz, quizRequest.getQuestion());

        savedQuiz.setQuestions(questions);

        return quizMapper.toDto(savedQuiz);
    }
}
