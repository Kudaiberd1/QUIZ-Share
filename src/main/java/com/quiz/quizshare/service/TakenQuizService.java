package com.quiz.quizshare.service;

import com.quiz.quizshare.dto.answer.AnswerDto;
import com.quiz.quizshare.dto.takenQuiz.TakenQuizCreateRequest;
import com.quiz.quizshare.dto.takenQuiz.TakenQuizResponse;
import com.quiz.quizshare.entity.Answer;
import com.quiz.quizshare.entity.Quiz;
import com.quiz.quizshare.entity.TakenQuiz;
import com.quiz.quizshare.mappers.TakenQuizMapper;
import com.quiz.quizshare.repositories.QuizRepository;
import com.quiz.quizshare.repositories.TakenQuizRepository;
import com.quiz.quizshare.repositories.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class    TakenQuizService {

    private final TakenQuizRepository takenQuizRepository;
    private final TakenQuizMapper takenQuizMapper;
    private final QuizRepository quizRepository;
    private final UserAnswerRepository userAnswerRepository;

    public TakenQuizResponse getResult(Long id) {
        TakenQuiz takenQuiz = takenQuizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Result not found by this id: %d", id)));
        return takenQuizMapper.toDto(takenQuiz);
    }

    public TakenQuizResponse createResult(TakenQuizCreateRequest result) {
        log.info(result.toString());
        Quiz quiz = quizRepository.findById(result.getQuiz())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Quiz not found by this id: %d", result.getQuiz())));
        TakenQuiz takenQuiz = new TakenQuiz();
        takenQuiz.setQuiz(quiz);
        takenQuiz.setCorrect(result.getCorrect());
        takenQuiz.setWrong(result.getWrong());
        takenQuiz.setSkipped(result.getSkipped());
        takenQuiz.setRating((result.getCorrect()*1.0)/quiz.getQuestions().size());
        takenQuiz.setAuthorId(result.getAuthorId());

        List<Answer> answers = new java.util.ArrayList<>(List.of());
        for(AnswerDto a : result.getUserAnswers()){
            Answer answer1 = new Answer();

            answer1.setQuestionIndex(a.getQuestionIndex());
            answer1.setSelectedOptions(a.getSelectedOptions());

            Answer newAnswer = userAnswerRepository.save(answer1);

            answers.add(newAnswer);

        }
        takenQuiz.setUserAnswers(answers);

        takenQuizRepository.save(takenQuiz);

        return takenQuizMapper.toDto(takenQuiz);
    }

    public List<TakenQuizResponse> getTakenQuizzesByUser(Integer id) {
        List<TakenQuiz> listQuizzes = takenQuizRepository.findAllByAuthorId(id, Sort.by(Sort.Direction.DESC, "id"));

        return listQuizzes.stream().map(takenQuizMapper::toDto).toList();
    }
}
