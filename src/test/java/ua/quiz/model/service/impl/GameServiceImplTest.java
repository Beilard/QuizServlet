package ua.quiz.model.service.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.dao.GameDao;
import ua.quiz.model.dao.PhaseDao;
import ua.quiz.model.dao.QuestionDao;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.dto.Question;
import ua.quiz.model.dto.Status;
import ua.quiz.model.entity.GameEntity;
import ua.quiz.model.entity.PhaseEntity;
import ua.quiz.model.entity.QuestionEntity;
import ua.quiz.model.entity.StatusEntity;
import ua.quiz.model.service.mapper.GameMapper;
import ua.quiz.model.service.mapper.PhaseMapper;
import ua.quiz.model.service.mapper.QuestionMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplTest {
    private static final Long QUESTION_ID = 1L;

    private static final Long PHASE_ID = 2L;

    private static final Long TEAM_ID = 3L;

    private static final Long GAME_ID = 4L;

    private static final int NUMBER_OF_QUESTIONS = 1;

    private static final int TIME_PER_QUESTION = 60;

    private static final long QUESTION_ENTRIES = 1L;

    private static final int CURRENT_PHASE = 0;

    private static final long GAME_ENTRIES = 1L;

    private static final int AMOUNT_OF_CORRECT_ANSWERS = 1;

    private static final String HINT = "Hint";

    private static final String QUESTION_BODY = "Question body";

    private static final String CORRECT_ANSWER = "Correct";

    private static final Question QUESTION = Question.builder()
            .withId(QUESTION_ID)
            .withBody(QUESTION_BODY)
            .withCorrectAnswer(CORRECT_ANSWER)
            .withHint(HINT)
            .build();

    private static final QuestionEntity QUESTION_ENTITY = QuestionEntity.builder()
            .withId(QUESTION_ID)
            .withBody(QUESTION_BODY)
            .withCorrectAnswer(CORRECT_ANSWER)
            .withHint(HINT)
            .build();

    private static final Phase PHASE = Phase.builder()
            .withId(PHASE_ID)
            .withQuestion(QUESTION)
            .build();

    private static final PhaseEntity PHASE_ENTITY = PhaseEntity.builder()
            .withId(PHASE_ID)
            .withQuestion(QUESTION_ENTITY)
            .build();

    private static final Game GAME = Game.builder()
            .withNumberOfQuestions(NUMBER_OF_QUESTIONS)
            .withTimePerQuestion(TIME_PER_QUESTION)
            .withTeamId(TEAM_ID)
            .withPhases(Collections.emptyList())
            .withStatus(Status.ONGOING)
            .build();

    private static final GameEntity GAME_ENTITY = GameEntity.builder()
            .withNumberOfQuestions(NUMBER_OF_QUESTIONS)
            .withTimePerQuestion(TIME_PER_QUESTION)
            .withTeamId(TEAM_ID)
            .withPhaseEntities(Collections.emptyList())
            .withStatusEntity(StatusEntity.ONGOING)
            .build();

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private GameServiceImpl gameService;
    @Mock
    private GameDao gameDao;
    @Mock
    private PhaseDao phaseDao;
    @Mock
    private QuestionDao questionDao;
    @Mock
    private GameMapper gameMapper;
    @Mock
    private PhaseMapper phaseMapper;
    @Mock
    private QuestionMapper questionMapper;

    @Test
    public void startGameShouldThrowExceptionDueToNullTeamId() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Either teamId is null or number of question/time per question" +
                " are less than 0 to start the game");

        gameService.startGame(null, NUMBER_OF_QUESTIONS, TIME_PER_QUESTION);
    }

    @Test
    public void startGameShouldThrowExceptionDueToWrongNumberOfQuestions() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Either teamId is null or number of question/time per question" +
                " are less than 0 to start the game");

        gameService.startGame(TEAM_ID, 0, TIME_PER_QUESTION);
    }

    @Test
    public void startGameShouldThrowExceptionDueToWrongTimePerQuestion() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Either teamId is null or number of question/time per question" +
                " are less than 0 to start the game");

        gameService.startGame(TEAM_ID, NUMBER_OF_QUESTIONS, 0);
    }

    @Test
    public void startGameShouldHaveNormalBehaviour() {
        when(questionDao.countEntries()).thenReturn(QUESTION_ENTRIES);
        when(gameMapper.mapGameToGameEntity(GAME)).thenReturn(GAME_ENTITY);
        when(gameDao.saveAndReturnId(GAME_ENTITY)).thenReturn(GAME_ID);
        when(phaseDao.findPhasesByGameId(GAME_ID)).thenReturn(singletonList(PHASE_ENTITY));
        when(phaseMapper.mapPhaseEntityToPhase(PHASE_ENTITY)).thenReturn(PHASE);
        when(questionDao.findById(QUESTION_ID)).thenReturn(Optional.of(QUESTION_ENTITY));
        when(questionMapper.mapQuestionEntityToQuestion(QUESTION_ENTITY)).thenReturn(QUESTION);

        final Game actual = gameService.startGame(TEAM_ID, NUMBER_OF_QUESTIONS, TIME_PER_QUESTION);
        final Game expected = Game.builder(GAME)
                .withId(GAME_ID)
                .withCurrentPhase(CURRENT_PHASE)
                .withPhases(singletonList(PHASE))
                .build();

        assertEquals(expected, actual);
    }

    @Test
    public void finishGameShouldThrowExceptionDueToNullGame() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Null game passed to finish");

        gameService.finishGame(null);
    }

    @Test
    public void finishGameShouldHaveNormalBehaviour() {
        final Game pendingGame = Game.builder(GAME)
                .withStatus(Status.PENDING)
                .build();

        when(gameMapper.mapGameToGameEntity(pendingGame)).thenReturn(GAME_ENTITY);

        gameService.finishGame(GAME);
        verify(gameDao).update(GAME_ENTITY);

    }

    @Test
    public void findByIdShouldThrowExceptionDueToNullId() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Null id passed to find a game");

        gameService.findById(null);
    }

    @Test
    public void findByIdShouldHaveNormalBehaviour() {
        when(gameMapper.mapGameEntityToGame(GAME_ENTITY)).thenReturn(GAME);
        when(gameDao.findById(GAME_ID)).thenReturn(Optional.of(GAME_ENTITY));

        Game actual = gameService.findById(GAME_ID);

        assertEquals(GAME, actual);
    }

    @Test
    public void findAllShouldHaveNormalBehaviour() {
        when(gameDao.findAll(anyLong(), anyLong())).thenReturn(singletonList(GAME_ENTITY));
        when(gameMapper.mapGameEntityToGame(GAME_ENTITY)).thenReturn(GAME);

        List<Game> actual = gameService.findAll(1L, 10L);

        assertEquals(singletonList(GAME), actual);
    }

    @Test
    public void findAllShouldReturnEmptyList() {
        when(gameDao.findAll(anyLong(), anyLong())).thenReturn(emptyList());

        List<Game> actual = gameService.findAll(1L, 10L);
        assertEquals(emptyList(), actual);
    }

    @Test
    public void countAllEntriesShouldHaveNormalBehaviour() {
        when(gameDao.countEntries()).thenReturn(GAME_ENTRIES);
        final long actual = gameService.countAllEntries();

        verify(gameDao).countEntries();
        assertEquals(GAME_ENTRIES, actual);
    }

    @Test
    public void countAllByTeamIdShouldThrowExceptionDueToNullTeamId() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Null teamId passed to count games");

        gameService.countAllByTeamId(null);
    }

    @Test
    public void countAllByTeamIdShouldHaveNormalBehaviour() {
        when(gameDao.countAllByTeamId(TEAM_ID)).thenReturn(GAME_ENTRIES);
        final long actual = gameService.countAllByTeamId(TEAM_ID);

        verify(gameDao).countAllByTeamId(TEAM_ID);
        assertEquals(GAME_ENTRIES, actual);
    }

    @Test
    public void getCorrectAnswersCountShouldThrowExceptionDueToNullGame() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Null game passed to get correct amount of answers");

        gameService.getCorrectAnswersCount(null);
    }

    @Test
    public void getCorrectAnswersCountShouldHaveNormalBehaviour() {
        final Game game = Game.builder(GAME)
                .withPhases(singletonList(Phase.builder(PHASE)
                        .withCorrect(true)
                        .build()))
                .build();
        final long actual = gameService.getCorrectAnswersCount(game);
        assertEquals(AMOUNT_OF_CORRECT_ANSWERS, actual);
    }

    @Test
    public void updateGameShouldThrowExceptionDueToNullGame() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Null game passed to update a game");

        gameService.updateGame(null);
    }

    @Test
    public void updateGameShouldHaveNormalBehaviour() {
        when(gameMapper.mapGameToGameEntity(GAME)).thenReturn(GAME_ENTITY);
        gameService.updateGame(GAME);

        verify(gameDao).update(GAME_ENTITY);
    }

    @Test
    public void findAllByTeamIdShouldThrowExceptionDueToNullGame() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Null id passed to find games by team id");

        gameService.findAllByTeamId(null, null, null);
    }

    @Test
    public void findAllByTeamIdShouldHaveNormalBehaviour() {
        when(gameDao.findAllByTeamId(anyLong(), anyLong(), anyLong())).thenReturn(singletonList(GAME_ENTITY));
        when(gameMapper.mapGameEntityToGame(GAME_ENTITY)).thenReturn(GAME);

        List<Game> actual = gameService.findAllByTeamId(TEAM_ID, 1L, 10L);

        verify(gameDao).findAllByTeamId(anyLong(), anyLong(), anyLong());
        assertEquals(singletonList(GAME), actual);
    }

    @Test
    public void findAllByTeamIdShouldReturnEmptyList() {
        when(gameDao.findAllByTeamId(anyLong(), anyLong(), anyLong())).thenReturn(emptyList());

        List<Game> actual = gameService.findAllByTeamId(TEAM_ID, 1L, 10L);

        verify(gameDao).findAllByTeamId(anyLong(), anyLong(), anyLong());
        assertEquals(emptyList(), actual);
    }
}
