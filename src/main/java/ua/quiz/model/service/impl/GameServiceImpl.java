package ua.quiz.model.service.impl;

import org.apache.log4j.Logger;
import ua.quiz.model.dao.GameDao;
import ua.quiz.model.dao.PhaseDao;
import ua.quiz.model.dao.QuestionDao;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.dto.Question;
import ua.quiz.model.dto.Status;
import ua.quiz.model.entity.GameEntity;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.GameService;
import ua.quiz.model.service.mapper.GameMapper;
import ua.quiz.model.service.mapper.PhaseMapper;
import ua.quiz.model.service.mapper.QuestionMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class GameServiceImpl implements GameService {
    private static final Logger LOGGER = Logger.getLogger(GameServiceImpl.class);

    private final GameDao gameDao;
    private final PhaseDao phaseDao;
    private final QuestionDao questionDao;
    private final GameMapper gameMapper;
    private final PhaseMapper phaseMapper;
    private final QuestionMapper questionMapper;

    public GameServiceImpl(GameDao gameDao, PhaseDao phaseDao, QuestionDao questionDao, GameMapper gameMapper, PhaseMapper phaseMapper, QuestionMapper questionMapper) {
        this.gameDao = gameDao;
        this.phaseDao = phaseDao;
        this.questionDao = questionDao;
        this.gameMapper = gameMapper;
        this.phaseMapper = phaseMapper;
        this.questionMapper = questionMapper;
    }

    @Override
    public Game startGame(Long teamId, int numberOfQuestions, int timePerQuestion) {
        if (teamId == null || numberOfQuestions <= 0 || timePerQuestion <= 0) {
            LOGGER.warn("Either teamId is null or number of question/" +
                    "time per question are less than 0 to start the game");
            throw new IllegalArgumentException("Either teamId is null or number of question/time per question" +
                    " are less than 0 to start the game");
        }
        final Game game = gameBuilder(teamId, numberOfQuestions, timePerQuestion);
        final Long gameId = gameDao.saveAndReturnId(gameMapper.mapGameToGameEntity(game));

        return createGameWithPhases(numberOfQuestions, game, gameId);
    }

    @Override
    public void finishGame(Game game) {
        if (game == null) {
            LOGGER.warn("Null game passed to finish");
            throw new IllegalArgumentException("Null game passed to finish");
        }
        final Game finishedGame = changeStatusToPending(game);

        gameDao.update(gameMapper.mapGameToGameEntity(finishedGame));
    }

    @Override
    public Game startReview(Game game) {
        if (game == null || game.getStatus() != Status.PENDING) {
            LOGGER.warn("Game passed to start review is invalid");
            throw new IllegalArgumentException("Game passed to start review is invalid");
        }
        final Game gameWithSetPhases = changePhaseToZero(game);

        gameDao.update(gameMapper.mapGameToGameEntity(gameWithSetPhases));
        return gameWithSetPhases;
    }

    @Override
    public void finishReview(Game game) {
        if (game == null) {
            LOGGER.warn("Null game passed to  finish review");
            throw new IllegalArgumentException("Null game passed to finish review");
        }
        final Game reviewedGame = changeStatusToReviewed(game);

        gameDao.update(gameMapper.mapGameToGameEntity(reviewedGame));
    }

    @Override
    public Game findById(Long id) {
        if (id == null) {
            LOGGER.warn("Null id passed to find a game");
            throw new IllegalArgumentException("Null id passed to find a game");
        }

        return gameMapper.mapGameEntityToGame(gameDao.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<Game> findAll(Long page, Long rowCount) {
        Long offset = getOffset(page, rowCount);
        List<GameEntity> entities = gameDao.findAll(offset, rowCount);

        return entities.isEmpty() ? Collections.emptyList() :
                mapGameEntityListToGameList(entities);
    }

    @Override
    public Long countAllEntries() {
        return gameDao.countEntries();
    }

    @Override
    public Long countAllByTeamId(Long teamId) {
        if (teamId == null) {
            LOGGER.warn("Null teamId passed to count games");
            throw new IllegalArgumentException("Null teamId passed to count games");
        }
        return gameDao.countAllByTeamId(teamId);
    }

    @Override
    public Long getCorrectAnswersCount(Game game) {
        if (game == null) {
            LOGGER.warn("Null game passed to get correct amount of answers");
            throw new IllegalArgumentException("Null game passed to get correct amount of answers");
        }
        return game.getPhases()
                .stream()
                .filter(Phase::getCorrect)
                .count();
    }

    @Override
    public void updateGame(Game game) {
        if (game == null) {
            LOGGER.warn("Null game passed to update a game");
            throw new IllegalArgumentException("Null game passed to update a game");
        }
        gameDao.update(gameMapper.mapGameToGameEntity(game));
    }

    @Override
    public List<Game> findAllByTeamId(Long teamId, Long page, Long rowCount) {
        if (teamId == null) {
            LOGGER.warn("Null id passed to find games by team id");
            throw new IllegalArgumentException("Null id passed to find games by team id");
        }

        Long offset = getOffset(page, rowCount);
        List<GameEntity> gamesOfTeam = gameDao.findAllByTeamId(teamId, offset, rowCount);

        return gamesOfTeam.isEmpty() ? Collections.emptyList() :
                mapGameEntityListToGameList(gamesOfTeam);
    }

    private Game createGameWithPhases(int numberOfQuestions, Game game, Long gameId) {
        return Game.builder(game)
                .withId(gameId)
                .withCurrentPhase(0)
                .withPhases(returnPhaseList(gameId, numberOfQuestions))
                .build();
    }

    private List<Phase> returnPhaseList(Long gameId, Integer numberOfPhases) {
        Long amountOfQuestionsInDb = questionDao.countEntries();
        if (numberOfPhases > amountOfQuestionsInDb) {
            LOGGER.warn("Amount of questions requested is bigger than the count in DB");
            throw new IllegalArgumentException("Amount of questions requested is bigger than the count in DB");
        }
        saveGeneratedPhases(gameId, numberOfPhases, amountOfQuestionsInDb);

        return phaseDao.findPhasesByGameId(gameId)
                .stream()
                .map(phaseMapper::mapPhaseEntityToPhase)
                .collect(Collectors.toList());
    }

    private void saveGeneratedPhases(Long gameId, Integer numberOfPhases, Long amountOfQuestionsInDb) {
        List<Long> generatedIds = generateIds(numberOfPhases, amountOfQuestionsInDb);

        for (int i = 0; i < numberOfPhases; i++) {
            final Phase phase = generatePhase(gameId, generatedIds, i);

            phaseDao.save(phaseMapper.mapPhaseToPhaseEntity(phase));
        }
    }

    private Phase generatePhase(Long gameId, List<Long> generatedIds, int i) {
        final Question question = questionDao.findById(generatedIds.get(i))
                .map(questionMapper::mapQuestionEntityToQuestion)
                .orElseThrow(EntityNotFoundException::new);

        return Phase.builder()
                .withGameId(gameId)
                .withStartTime(LocalDateTime.now())
                .withDeadline(LocalDateTime.now())
                .withEndTime(LocalDateTime.now())
                .withQuestion(question)
                .build();
    }

    private List<Long> generateIds(Integer numberOfPhases, Long amountOfQuestionsInDb) {
        final Random random = new Random();
        return random
                .longs(1, amountOfQuestionsInDb + 1)
                .distinct()
                .limit(numberOfPhases)
                .boxed()
                .collect(Collectors.toList());
    }

    private Game gameBuilder(Long teamId, int numberOfQuestions, int timePerQuestion) {
        return Game.builder()
                .withNumberOfQuestions(numberOfQuestions)
                .withTimePerQuestion(timePerQuestion)
                .withTeamId(teamId)
                .withPhases(Collections.emptyList())
                .withStatus(Status.ONGOING)
                .build();
    }

    private long getOffset(Long page, Long rowCount) {
        return page * rowCount - rowCount;
    }

    private List<Game> mapGameEntityListToGameList(List<GameEntity> gameEntities) {
        return gameEntities.stream()
                .map(gameMapper::mapGameEntityToGame)
                .collect(Collectors.toList());
    }

    private Game changeStatusToPending(Game game) {
        return Game.builder(game)
                .withStatus(Status.PENDING)
                .build();
    }

    private Game changeStatusToReviewed(Game game) {
        return Game.builder(game)
                .withStatus(Status.REVIEWED)
                .build();
    }

    private Game changePhaseToZero(Game game) {
        return Game.builder(game)
                .withCurrentPhase(0)
                .build();
    }
}
