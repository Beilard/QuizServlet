package ua.quiz.model.service.mapper;

import ua.quiz.model.dto.Question;
import ua.quiz.model.entity.QuestionEntity;

public class QuestionMapper {
    public Question mapQuestionEntityToQuestion(QuestionEntity entity) {
        if (entity == null) {
            return null;
        }

        return Question.builder()
                .withId(entity.getId())
                .withBody(entity.getBody())
                .withCorrectAnswer(entity.getCorrectAnswer())
                .withHint(entity.getHint())
                .build();
    }

    public QuestionEntity mapQuestionToQuestionEntity(Question question) {
        return QuestionEntity.builder()
                .withId(question.getId())
                .withBody(question.getBody())
                .withCorrectAnswer(question.getCorrectAnswer())
                .withHint(question.getHint())
                .build();
    }
}
