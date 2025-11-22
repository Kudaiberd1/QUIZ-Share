package com.quiz.QUIZ_Share.mappers;

import com.quiz.QUIZ_Share.dto.takenQuiz.TakenQuizResponse;
import com.quiz.QUIZ_Share.entity.Answer;
import com.quiz.QUIZ_Share.entity.Quiz;
import com.quiz.QUIZ_Share.entity.TakenQuiz;
import com.quiz.QUIZ_Share.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TakenQuizMapper {
    @Mapping(target = "statuses", expression = "java(calculateStatuses(entity.getQuiz(), entity.getUserAnswers()))")
    TakenQuizResponse toDto(TakenQuiz entity);

    default List<Status> calculateStatuses(Quiz quiz, List<Answer> answers) {
        List<Status> result = new ArrayList<>();

        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            List<Integer> correct = quiz.getQuestions().get(i).getAnswer();
            List<Integer> selected = answers.get(i).getSelectedOptions();

            if (selected == null || selected.isEmpty()) {
                result.add(Status.MISSED);
                continue;
            }
            if (sameIgnoringOrder(correct, selected)) {
                result.add(Status.CORRECT);
            } else {
                result.add(Status.WRONG);
            }
        }

        return result;
    }

    default boolean sameIgnoringOrder(List<Integer> a, List<Integer> b) {
        if (a.size() != b.size()) return false;
        return new HashSet<>(a).equals(new HashSet<>(b));
    }

}


