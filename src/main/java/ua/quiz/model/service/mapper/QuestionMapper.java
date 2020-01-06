package ua.quiz.model.service.mapper;

import ua.quiz.model.domain.Question;
import ua.quiz.model.entity.QuestionEntity;

public class QuestionMapper {
    public Question mapQuestionEntityToQuestion(QuestionEntity questionEntity) {
        if (questionEntity == null) {
            return null;
        }
        return Question.builder()
                .withId(questionEntity.getId())
                .withBody(questionEntity.getBody())
                .withAnswers(questionEntity.getAnswers())
                .withStartTime(questionEntity.getStartTime())
                .withEndTime(questionEntity.getStartTime())
                .withHint(questionEntity.getHint())
                .build();
    }

    public QuestionEntity mapQuestionToQuestionEntity(Question question) {
        return QuestionEntity.builder()
                .withId(question.getId())
                .withBody(question.getBody())
                .withAnswers(question.getAnswers())
                .withStartTime(question.getStartTime())
                .withEndTime(question.getStartTime())
                .withHint(question.getHint())
                .build();
    }
}
