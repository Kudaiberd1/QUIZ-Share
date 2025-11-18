package com.quiz.QUIZ_Share.service;

import com.quiz.QUIZ_Share.dto.question.QuestionUpdateRequest;
import com.quiz.QUIZ_Share.dto.quiz.QuizCreateRequest;
import com.quiz.QUIZ_Share.dto.quiz.QuizResponse;
import com.quiz.QUIZ_Share.dto.quiz.QuizUpdateRequest;
import com.quiz.QUIZ_Share.entity.Questions;
import com.quiz.QUIZ_Share.entity.Quiz;
import com.quiz.QUIZ_Share.entity.User;
import com.quiz.QUIZ_Share.enums.Filter;
import com.quiz.QUIZ_Share.exceptions.GlobalExceptionHandler;
import com.quiz.QUIZ_Share.mappers.QuizMapper;
import com.quiz.QUIZ_Share.repositories.QuestionRepository;
import com.quiz.QUIZ_Share.repositories.QuizRepository;
import com.quiz.QUIZ_Share.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final VariantService variantService;

    public List<QuizResponse> getAll() {
        log.info("Get all quizzes");

        List<Quiz> quizzes = quizRepository.findAllByOrderByAddedTimeDesc();

        for (Quiz q : quizzes) {
            log.info("Quiz {} has {} questions", q.getTitle(), q.getQuestions());
        }
        return quizzes.stream().map(quizMapper::toDto).toList();
    }

    public QuizResponse getById(Long id) {
        log.info("Get quiz by id {}", id);
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        return quizMapper.toDto(quiz);
    }


    public QuizResponse createQuiz(@Valid QuizCreateRequest quizCreateRequest, MultipartFile file) {
        log.info("Create quiz {}", quizCreateRequest);

        User user = userRepository.findById(quizCreateRequest.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        Quiz quiz = new Quiz();
        quiz.setTitle(quizCreateRequest.getTitle());
        quiz.setDescription(quizCreateRequest.getDescription());
        quiz.setSubject(quizCreateRequest.getSubject());
        quiz.setDifficulty(quizCreateRequest.getDifficulty());
        quiz.setPrivacy(quizCreateRequest.getPrivacy());
        quiz.setUserId(user.getId());
        quiz.setFirstName(user.getFirstName());
        quiz.setLastName(user.getLastName());

        String url;
        if(file != null){
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                log.info("url: {} file: {}", fileName, file);
                Path uploadDir = Paths.get("uploads");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                Path path = uploadDir.resolve(fileName);
                Files.write(path, file.getBytes());
                url = "http://localhost:8080/uploads/" + fileName;
                log.info("url: {} file: {}", url, file);

            } catch (IOException e) {
                throw new GlobalExceptionHandler.ImageUploadException("Failed to upload image");
            }
        }else{
            url="";
        }

        quiz.setImageUrl(url);

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


        if(quizUpdateRequest.getTitle() != null) quiz.setTitle(quizUpdateRequest.getTitle());
        if(quizUpdateRequest.getDescription() != null) quiz.setDescription(quizUpdateRequest.getDescription());
        if(quizUpdateRequest.getSubject() != null) quiz.setSubject(quizUpdateRequest.getSubject());
        if(quizUpdateRequest.getPrivacy() != null) quiz.setPrivacy(quizUpdateRequest.getPrivacy());
        if(quizUpdateRequest.getDifficulty() != null) quiz.setDifficulty(quizUpdateRequest.getDifficulty());

        if(quizUpdateRequest.getRate() != null){
            List<Integer> rate = quiz.getRate();
            rate.set(quizUpdateRequest.getRate()-1, rate.get(quizUpdateRequest.getRate()-1)+1);
            quiz.setRate(rate);
        }

        if(quizUpdateRequest.getQuestions() != null) {
            List<Questions> updatedQuestions = new ArrayList<>();
            for (QuestionUpdateRequest q : quizUpdateRequest.getQuestions()) {
                updatedQuestions.add(questionService.updateQuestion(q));
            }
            quiz.setQuestions(updatedQuestions);
        }


        log.info("Quiz updated {}", quizUpdateRequest);

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

    public List<QuizResponse> filterQuiz(String title, String subject, Filter filter) {
         Sort sort = switch(filter) {
            case LATEST -> Sort.by(Sort.Direction.DESC, "addedTime");
            case OLD -> Sort.by(Sort.Direction.ASC, "addedTime");
            case RATE -> Sort.by(Sort.Direction.DESC, "rate");
        };
        List<Quiz> filteredQuizzes = quizRepository.filterQuiz(title, subject, sort);
        return filteredQuizzes.stream().map(quizMapper::toDto).toList();
    }

    public List<String> getAllSubject() {
        List<String> subjects = quizRepository.findAllSubject();
        return subjects;
    }
}
