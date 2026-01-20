package com.quiz.QUIZ_Share.service;

import com.quiz.QUIZ_Share.dto.question.QuestionRequest;
import com.quiz.QUIZ_Share.dto.question.QuestionUpdateRequest;
import com.quiz.QUIZ_Share.entity.Questions;
import com.quiz.QUIZ_Share.entity.Quiz;
import com.quiz.QUIZ_Share.entity.Variant;
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
    private final VariantService variantService;

    public List<Questions> buildQuestion(Quiz quiz, Set<QuestionRequest> questions){
        List<Questions> newQuestions = new ArrayList<>();

        for(QuestionRequest question: questions){
            Questions newQuestion = new Questions();
            newQuestion.setQuestion(question.getQuestion());
            newQuestion.setAnswer(question.getAnswer());
            newQuestion.setQuiz(quiz);

            var savedQuestion = questionRepository.save(newQuestion);

            newQuestion.setVariants(variantService.createVariant(question.getVariants(), savedQuestion));

            newQuestions.add(newQuestion);
        }

        return newQuestions;
    }

    public Questions updateQuestion(QuestionUpdateRequest questionRequest){
        Questions existingQuestion = questionRepository.findById(questionRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Question id not found"));

        List<Variant> allVariants = questionRequest.getVariants();

        if(questionRequest.getNewVariants() != null && !questionRequest.getNewVariants().isEmpty()) {
            var newVariants = variantService.createVariant(questionRequest.getNewVariants(), existingQuestion);
            allVariants.addAll(newVariants);
        }
        existingQuestion.setVariants(allVariants);
        existingQuestion.setAnswer(questionRequest.getAnswer());

        return existingQuestion;
    }
}
