package ua.quiz.model.service.mapper;

import org.junit.Test;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.dto.Status;
import ua.quiz.model.entity.GameEntity;
import ua.quiz.model.entity.PhaseEntity;
import ua.quiz.model.entity.StatusEntity;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameMapperTest {
    private static final Long ID = 0L;

    private static final int NUMBER_OF_QUESTIONS = 3;

    private static final int TIME_PER_QUESTION = 5;

    private static final Long TEAM_ID = 7L;

    private static final Status STATUS = Status.ONGOING;

    private static final StatusEntity STATUS_ENTITY = StatusEntity.ONGOING;

    private static final List<Phase> PHASES = Collections.singletonList(Phase.builder().build());

    private static final List<PhaseEntity> PHASE_ENTITIES = Collections.singletonList(PhaseEntity.builder().build());

    private final GameMapper gameMapper = new GameMapper();

    @Test
    public void mapGameEntityToGameShouldReturnGame() {
        final GameEntity gameEntity = GameEntity.builder()
                .withId(ID)
                .withNumberOfQuestions(NUMBER_OF_QUESTIONS)
                .withTimePerQuestion(TIME_PER_QUESTION)
                .withTeamId(TEAM_ID)
                .withStatusEntity(StatusEntity.valueOf(STATUS.name()))
                .withPhaseEntities(PHASE_ENTITIES)
                .build();

        final Game game = gameMapper.mapGameEntityToGame(gameEntity);
        assertThat("mapping id has failed", game.getId(), is(ID));
        assertThat("mapping number of questions has failed", game.getNumberOfQuestions(), is(NUMBER_OF_QUESTIONS));
        assertThat("mapping time per question has failed", game.getTimePerQuestion(), is(TIME_PER_QUESTION));
        assertThat("mapping teamId has failed", game.getTeamId(), is(TEAM_ID));
        assertThat("mapping status has failed", game.getStatus(), is(STATUS));
        assertThat("mapping phaseList has failed", game.getPhases(), is(PHASES));
    }


    @Test
    public void mapGameToGameEntityShouldReturnGameEntity() {
        final Game game = Game.builder()
                .withId(ID)
                .withNumberOfQuestions(NUMBER_OF_QUESTIONS)
                .withTimePerQuestion(TIME_PER_QUESTION)
                .withTeamId(TEAM_ID)
                .withStatus(Status.valueOf(STATUS.name()))
                .withPhases(PHASES)
                .build();

        final GameEntity gameEntity = gameMapper.mapGameToGameEntity(game);
        assertThat("mapping id has failed", gameEntity.getId(), is(ID));
        assertThat("mapping number of questions has failed", gameEntity.getNumberOfQuestions(), is(NUMBER_OF_QUESTIONS));
        assertThat("mapping time per question has failed", gameEntity.getTimePerQuestion(), is(TIME_PER_QUESTION));
        assertThat("mapping teamId has failed", gameEntity.getTeamId(), is(TEAM_ID));
        assertThat("mapping status has failed", gameEntity.getStatusEntity(), is(STATUS_ENTITY));
        assertThat("mapping phaseList has failed", gameEntity.getPhaseEntities(), is(PHASE_ENTITIES));
    }
}