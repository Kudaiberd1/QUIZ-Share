package com.quiz.QUIZ_Share.service;

import com.quiz.QUIZ_Share.dto.question.QuestionRequest;
import com.quiz.QUIZ_Share.entity.Questions;
import com.quiz.QUIZ_Share.entity.Quiz;
import com.quiz.QUIZ_Share.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Questions> buildQuestion(Quiz quiz, Set<QuestionRequest> questions){

        List<Questions> newQuestions = new ArrayList<>();

        for(QuestionRequest qustion: questions){
            Questions newQuestion = new Questions();
            newQuestion.setQuestion(qustion.getQuestion());
            newQuestion.setAnswer(qustion.getAnswer());
            newQuestion.setOptionA(qustion.getOptionA());
            newQuestion.setOptionB(qustion.getOptionB());
            newQuestion.setOptionC(qustion.getOptionC());
            newQuestion.setOptionD(qustion.getOptionD());
            newQuestion.setQuiz(quiz);

            newQuestions.add(newQuestion);
        }

        return questionRepository.saveAll(newQuestions);
    }
}
