package ua.quiz.model.service.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.dao.PhaseDao;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.entity.PhaseEntity;
import ua.quiz.model.service.mapper.PhaseMapper;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PhaseServiceImplTest {
    private static final Long ID = 1L;

    private static final Long GAME_ID = 2L;

    private static final int DEFAULT_TIME_PER_QUESTION = 60;

    private static final String ANSWER = "Answer";

    private static final Phase PHASE_EXAMPLE = Phase.builder()
            .withId(ID)
            .withGameId(GAME_ID)
            .build();

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private PhaseServiceImpl phaseService;
    @Mock
    private PhaseDao phaseDao;
    @Mock
    private PhaseMapper phaseMapper;

    @Test
    public void initiatePhaseShouldThrowExceptionDueToNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Passed phase is null or timePerQuestion is invalid");

        phaseService.initiatePhase(null, null);
    }

    @Test
    public void initiatePhaseShouldHaveNormalBehaviour() {
        final PhaseEntity entity = PhaseEntity.builder()
                .withId(ID)
                .withStartTime(LocalDateTime.now())
                .withDeadline(LocalDateTime.now().plusSeconds(DEFAULT_TIME_PER_QUESTION))
                .withHintUsed(false)
                .withCorrect(false)
                .build();

        when(phaseMapper.mapPhaseToPhaseEntity(any())).thenReturn(entity);
        phaseService.initiatePhase(PHASE_EXAMPLE, DEFAULT_TIME_PER_QUESTION);

        verify(phaseDao).update(entity);
    }

    @Test
    public void finishPhaseShouldThrowExceptionDueToNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Passed phase is null");

        phaseService.finishPhase(null, null);
    }

    @Test
    public void finishPhaseShouldHaveNormalBehaviour() {
        final PhaseEntity entity = PhaseEntity.builder()
                .withId(ID)
                .withEndTime(LocalDateTime.now())
                .withGivenAnswer(ANSWER)
                .build();

        when(phaseMapper.mapPhaseToPhaseEntity(any())).thenReturn(entity);
        phaseService.finishPhase(PHASE_EXAMPLE, ANSWER);

        verify(phaseDao).update(entity);
    }

    @Test
    public void reviewPhasePositivelyShouldThrowExceptionDueToNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Passed phase to positively review is null");

        phaseService.reviewPhasePositively(null);
    }

    @Test
    public void reviewPhasePositivelyShouldHaveNormalBehaviour() {
        final PhaseEntity entity = PhaseEntity.builder()
                .withCorrect(true)
                .build();

        when(phaseMapper.mapPhaseToPhaseEntity(any())).thenReturn(entity);
        phaseService.reviewPhasePositively(PHASE_EXAMPLE);

        verify(phaseDao).update(entity);
    }

    @Test
    public void useHintShouldThrowExceptionDueToNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Passed phase to turn on hint is null");

        phaseService.useHint(null);
    }

    @Test
    public void useHintShouldHaveNormalBehaviour() {
        final PhaseEntity entity = PhaseEntity.builder()
                .withHintUsed(true)
                .build();

        when(phaseMapper.mapPhaseToPhaseEntity(any())).thenReturn(entity);
        phaseService.useHint(PHASE_EXAMPLE);

        verify(phaseDao).update(entity);
    }
}
