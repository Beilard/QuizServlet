package ua.quiz.model.service.mapper;

import org.junit.Test;
import ua.quiz.model.dto.Question;
import ua.quiz.model.entity.QuestionEntity;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class QuestionMapperTest {
    private static final Long ID = 0L;

    private static final String BODY = "BODY";

    private static final String CORRECT_ANSWER = "ANSWER";

    private static final String HINT = "HINT";

    private final QuestionMapper questionMapper = new QuestionMapper();

    @Test
    public void mapQuestionEntityToQuestionShouldReturnQuestion() {
        final QuestionEntity questionEntity = QuestionEntity.builder()
                .withId(ID)
                .withBody(BODY)
                .withCorrectAnswer(CORRECT_ANSWER)
                .withHint(HINT)
                .build();

        final Question question = questionMapper.mapQuestionEntityToQuestion(questionEntity);
        assertThat("mapping id has failed", question.getId(), is(ID));
        assertThat("mapping body has failed", question.getBody(), is(BODY));
        assertThat("mapping correct answer has failed", question.getCorrectAnswer(), is(CORRECT_ANSWER));
        assertThat("mapping hint has failed", question.getHint(), is(HINT));
    }

    @Test
    public void mapQuestionToQuestionEntityShouldReturnQuestionEntity() {
        final Question question = Question.builder()
                .withId(ID)
                .withBody(BODY)
                .withCorrectAnswer(CORRECT_ANSWER)
                .withHint(HINT)
                .build();

        final QuestionEntity questionEntity = questionMapper.mapQuestionToQuestionEntity(question);
        assertThat("mapping id has failed", questionEntity.getId(), is(ID));
        assertThat("mapping body has failed", questionEntity.getBody(), is(BODY));
        assertThat("mapping correct answer has failed", questionEntity.getCorrectAnswer(), is(CORRECT_ANSWER));
        assertThat("mapping hint has failed", questionEntity.getHint(), is(HINT));
    }
}