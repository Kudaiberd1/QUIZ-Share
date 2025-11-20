package com.quiz.QUIZ_Share.service;

import com.quiz.QUIZ_Share.dto.takenQuiz.TakenQuizCreateRequest;
import com.quiz.QUIZ_Share.dto.takenQuiz.TakenQuizResponse;
import com.quiz.QUIZ_Share.entity.Quiz;
import com.quiz.QUIZ_Share.entity.TakenQuiz;
import com.quiz.QUIZ_Share.mappers.TakenQuizMapper;
import com.quiz.QUIZ_Share.repositories.QuizRepository;
import com.quiz.QUIZ_Share.repositories.TakenQuizRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TakenQuizService {

    private final TakenQuizRepository takenQuizRepository;
    private final TakenQuizMapper takenQuizMapper;
    private final QuizRepository quizRepository;

    public TakenQuizResponse getResult(Long id) {
        TakenQuiz takenQuiz = takenQuizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Result not found by this id: %d", id)));
        return takenQuizMapper.toDto(takenQuiz);
    }

    public TakenQuizResponse createResult(TakenQuizCreateRequest result) {
        Quiz quiz = quizRepository.findById(result.getQuiz())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Quiz not found by this id: %d", result.getQuiz())));
        TakenQuiz takenQuiz = new TakenQuiz();
        takenQuiz.setQuiz(quiz);
        takenQuiz.setCorrect(result.getCorrect());
        takenQuiz.setWrong(result.getWrong());
        takenQuiz.setSkipped(result.getSkipped());
        takenQuiz.setRating((result.getCorrect()*1.0)/quiz.getQuestions().size());
        takenQuiz.setAuthorId(result.getAuthorId());
        takenQuiz.setUserAnswers(result.getUserAnswers());

        takenQuizRepository.save(takenQuiz);

        return takenQuizMapper.toDto(takenQuiz);
    }
}
