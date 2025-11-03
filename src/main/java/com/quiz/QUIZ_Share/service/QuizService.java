package com.quiz.QUIZ_Share.service;

import com.quiz.QUIZ_Share.dto.question.QuestionRequest;
import com.quiz.QUIZ_Share.dto.quiz.QuizCreateRequest;
import com.quiz.QUIZ_Share.dto.quiz.QuizResponse;
import com.quiz.QUIZ_Share.dto.quiz.QuizUpdateRequest;
import com.quiz.QUIZ_Share.entity.Questions;
import com.quiz.QUIZ_Share.entity.Quiz;
import com.quiz.QUIZ_Share.entity.User;
import com.quiz.QUIZ_Share.mappers.QuizMapper;
import com.quiz.QUIZ_Share.repositories.QuestionRepository;
import com.quiz.QUIZ_Share.repositories.QuizRepository;
import com.quiz.QUIZ_Share.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private final QuestionService questionService;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

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


    public QuizResponse createQuiz(@Valid QuizCreateRequest quizCreateRequest) {
        log.info("Create quiz {}", quizCreateRequest);

        User user = userRepository.findById(quizCreateRequest.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        Quiz quiz = new Quiz();
        quiz.setTitle(quizCreateRequest.getTitle());
        quiz.setDescription(quizCreateRequest.getDescription());
        quiz.setSubject(quizCreateRequest.getSubject());
        quiz.setDifficulty(quizCreateRequest.getDifficulty());
        quiz.setPrivacy(quizCreateRequest.getPrivacy());
        quiz.setUser(user);

        var savedQuiz = quizRepository.save(quiz);

        var questions = questionService.buildQuestion(quiz, quizCreateRequest.getQuestion());

        savedQuiz.setQuestions(questions);

        return quizMapper.toDto(savedQuiz);
    }

    public QuizResponse updateQuiz(Long id, @Valid QuizUpdateRequest quizUpdateRequest) {
        log.info("Update quiz {}", quizUpdateRequest);


        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        User user = userRepository.findById(quizUpdateRequest.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        quiz.setTitle(quizUpdateRequest.getTitle());
        quiz.setDescription(quizUpdateRequest.getDescription());
        quiz.setSubject(quizUpdateRequest.getSubject());
        quiz.setDifficulty(quizUpdateRequest.getDifficulty());
        quiz.setPrivacy(quizUpdateRequest.getPrivacy());
        quiz.setUser(user);

        List<Questions> updatedQuestions = new ArrayList<>();
        for (Integer q : quizUpdateRequest.getQuestionIds()) {
            Questions existingQuestion = questionRepository.findById(q)
                    .orElseThrow(() -> new IllegalArgumentException("Question id not found"));
            updatedQuestions.add(existingQuestion);
        }
        quiz.setQuestions(updatedQuestions);

        if (quizUpdateRequest.getNewQuestion() != null) {
            var newQuestions = questionService.buildQuestion(quiz, quizUpdateRequest.getNewQuestion());
            quiz.getQuestions().addAll(newQuestions);
        }

        var savedQuiz = quizRepository.save(quiz);
        return quizMapper.toDto(savedQuiz);
    }

    public void deleteQuiz(Long id) {
        log.info("Delete quiz {}", id);
        quizRepository.deleteById(id);
    }
}
