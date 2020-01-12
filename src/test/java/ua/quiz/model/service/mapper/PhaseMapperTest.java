package ua.quiz.model.service.mapper;

import org.junit.Test;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.entity.PhaseEntity;
import ua.quiz.model.entity.QuestionEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PhaseMapperTest {
    private static final Long ID = 0L;

    private static final LocalDateTime START_TIME = LocalDateTime.of(1995, 2, 17, 2, 2,0);

    private static final LocalDateTime END_TIME = LocalDateTime.of(1995, 2, 17, 2, 3,0);

    private static final LocalDateTime DEADLINE = LocalDateTime.of(1995, 2, 17, 2, 4,0);

    private static final boolean HINT_USED = false;

    private static final boolean IS_CORRECT = true;

    private static final String GIVEN_ANSWER = "ANSWER";

    private static final Long GAME_ID = 1L;

    private final PhaseMapper phaseMapper = new PhaseMapper();

    @Test
    public void mapPhaseEntityToPhaseShouldReturnPhase() {
        final PhaseEntity phaseEntity = PhaseEntity.builder()
                .withId(ID)
                .withStartTime(START_TIME)
                .withEndTime(END_TIME)
                .withDeadline(DEADLINE)
                .withHintUsed(HINT_USED)
                .withCorrect(IS_CORRECT)
                .withGivenAnswer(GIVEN_ANSWER)
                .withGameId(GAME_ID)
                .build();

        final Phase phase = phaseMapper.mapQuestionEntityToQuestion(phaseEntity);
        assertThat("mapping id has failed", phase.getId(), is(ID));
        assertThat("mapping startTime has failed", phase.getStartTime(), is(START_TIME));
        assertThat("mapping endTime has failed", phase.getEndTime(), is(END_TIME));
        assertThat("mapping deadline has failed", phase.getDeadline(), is(DEADLINE));
        assertThat("mapping hintUsed has failed", phase.getHintUsed(), is(HINT_USED));
        assertThat("mapping isCorrect has failed", phase.getCorrect(), is(IS_CORRECT));
        assertThat("mapping givenAnswer has failed", phase.getGivenAnswer(), is(GIVEN_ANSWER));
        assertThat("mapping gameId has failed", phase.getGameId(), is(GAME_ID));
    }

    @Test
    public void mapPhaseToPhaseEntityShouldReturnPhaseEntity() {
        final Phase phase = Phase.builder()
                .withId(ID)
                .withStartTime(START_TIME)
                .withEndTime(END_TIME)
                .withDeadline(DEADLINE)
                .withHintUsed(HINT_USED)
                .withCorrect(IS_CORRECT)
                .withGivenAnswer(GIVEN_ANSWER)
                .withGameId(GAME_ID)
                .build();

        final PhaseEntity phaseEntity = phaseMapper.mapQuestionToQuestionEntity(phase);
        assertThat("mapping id has failed", phaseEntity.getId(), is(ID));
        assertThat("mapping startTime has failed", phaseEntity.getStartTime(), is(START_TIME));
        assertThat("mapping endTime has failed", phaseEntity.getEndTime(), is(END_TIME));
        assertThat("mapping deadline has failed", phaseEntity.getDeadline(), is(DEADLINE));
        assertThat("mapping hintUsed has failed", phaseEntity.getHintUsed(), is(HINT_USED));
        assertThat("mapping isCorrect has failed", phaseEntity.getCorrect(), is(IS_CORRECT));
        assertThat("mapping givenAnswer has failed", phaseEntity.getGivenAnswer(), is(GIVEN_ANSWER));
        assertThat("mapping gameId has failed", phaseEntity.getGameId(), is(GAME_ID));
    }
}