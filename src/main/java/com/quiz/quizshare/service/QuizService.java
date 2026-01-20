package com.quiz.quizshare.service;

import com.quiz.quizshare.dto.question.QuestionUpdateRequest;
import com.quiz.quizshare.dto.quiz.QuizCreateRequest;
import com.quiz.quizshare.dto.quiz.QuizResponse;
import com.quiz.quizshare.dto.quiz.QuizUpdateRequest;
import com.quiz.quizshare.entity.Feedback;
import com.quiz.quizshare.entity.Questions;
import com.quiz.quizshare.entity.Quiz;
import com.quiz.quizshare.entity.TakenQuiz;
import com.quiz.quizshare.entity.User;
import com.quiz.quizshare.enums.Filter;
import com.quiz.quizshare.mappers.QuizMapper;
import com.quiz.quizshare.repositories.FeedbackRepository;
import com.quiz.quizshare.repositories.QuestionRepository;
import com.quiz.quizshare.repositories.QuizRepository;
import com.quiz.quizshare.repositories.TakenQuizRepository;
import com.quiz.quizshare.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final FeedbackRepository feedbackRepository;
    private final TakenQuizRepository takenQuizRepository;
    private final S3Service s3Service;

    public List<QuizResponse> getAll() {
        log.info("Get all quizzes");
        List<Quiz> quizzes = quizRepository.findAllByOrderByAddedTimeDesc();

        return quizzes.stream().map(quizMapper::toDto).toList();
    }

    public QuizResponse getById(Long id) {
        log.info("Get quiz by id {}", id);
        Quiz quiz = quizRepository.findById(id).orElseThrow();

        return quizMapper.toDto(quiz);
    }


    public QuizResponse createQuiz(@Valid QuizCreateRequest quizCreateRequest, MultipartFile file) {
        log.info("Create quiz");

        User user = userRepository.findById(quizCreateRequest.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("User not found by this id: %d", quizCreateRequest.getAuthorId())));


        Quiz quiz = new Quiz();
        quiz.setTitle(quizCreateRequest.getTitle());
        quiz.setDescription(quizCreateRequest.getDescription());
        quiz.setSubject(quizCreateRequest.getSubject());
        quiz.setDifficulty(quizCreateRequest.getDifficulty());
        quiz.setPrivacy(quizCreateRequest.getPrivacy());
        quiz.setUserId(user.getId());
        quiz.setFirstName(user.getFirstName());
        quiz.setLastName(user.getLastName());
        quiz.setTakeTimeLimit(quizCreateRequest.getTakeTimeLimit());

        String url=s3Service.uploadAndGetAddress(file);
        quiz.setImageUrl(url);

        if(quizCreateRequest.getQuestion() == null || quizCreateRequest.getQuestion().isEmpty()){
            throw new IllegalArgumentException("Quiz must contain at least one question");
        }

        var savedQuiz = quizRepository.save(quiz);
        var questions = questionService.buildQuestion(quiz, quizCreateRequest.getQuestion());
        savedQuiz.setQuestions(questions);
        return quizMapper.toDto(savedQuiz);
    }

    public QuizResponse updateQuiz(Long id, @Valid QuizUpdateRequest quizUpdateRequest) {
        log.info("Update quiz");


        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

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

        if (quizUpdateRequest.getNewQuestion() != null) {
            var newQuestions = questionService.buildQuestion(quiz, quizUpdateRequest.getNewQuestion());
            quiz.getQuestions().addAll(newQuestions);
        }

        var savedQuiz = quizRepository.save(quiz);
        return quizMapper.toDto(savedQuiz);
    }

    public void deleteQuiz(Long id) {
        log.info("Delete quiz {}", id);

        List<Feedback> feedbacks = feedbackRepository.findAllByQuizId(id);
        if (!feedbacks.isEmpty()) {
            feedbackRepository.deleteAll(feedbacks);
        }

        List<TakenQuiz> takenQuizzes = takenQuizRepository.findAllByQuizId(id);
        if (!takenQuizzes.isEmpty()) {
            takenQuizRepository.deleteAll(takenQuizzes);
        }

        List<Questions> questions = questionRepository.findAllByQuizId(id);
        if (!questions.isEmpty()) {
            questionRepository.deleteAll(questions);
        }

        if (!quizRepository.existsById(id)) {
            throw new IllegalArgumentException("Quiz not found with id: " + id);
        }

        quizRepository.deleteById(id);
        log.info("Quiz {} deleted successfully", id);
    }

    public List<QuizResponse> filterQuiz(String title, String subject, Filter filter) {
         Sort sort = switch(filter) {
            case LATEST -> Sort.by(Sort.Direction.DESC, "addedTime");
            case OLD -> Sort.by(Sort.Direction.ASC, "addedTime");
            case RATE -> Sort.by(Sort.Direction.ASC, "rate");
        };
        List<Quiz> filteredQuizzes = quizRepository.filterQuiz(title, subject, sort);
        return filteredQuizzes.stream().map(quizMapper::toDto).toList();
    }

    public List<String> getAllSubject() {
        return quizRepository.findAllSubject();
    }
}
