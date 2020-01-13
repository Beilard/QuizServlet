package ua.quiz.model.service.impl;

import org.apache.log4j.Logger;
import ua.quiz.model.dao.GameDao;
import ua.quiz.model.dto.Game;
import ua.quiz.model.service.GameService;
import ua.quiz.model.service.mapper.GameMapper;

import java.util.List;

public class GameServiceImpl implements GameService {
    private static final Logger LOGGER = Logger.getLogger(GameServiceImpl.class);

    private final GameDao gameDao;
    private final GameMapper gameMapper;

    public GameServiceImpl(GameDao gameDao, GameMapper gameMapper) {
        this.gameDao = gameDao;
        this.gameMapper = gameMapper;
    }

    @Override
    public Game startGame(Long teamId, int numberOfQuestions, int timePerQuestion) {
        return null;
    }

    @Override
    public void finishGame(Game game) {

    }

    @Override
    public Game reviewGame(Game game) {
        return null;
    }

    @Override
    public Game findById(Long id) {
        return null;
    }

    @Override
    public List<Game> findAll() {
        return null;
    }

    @Override
    public List<Game> findAllByTeamId(Long teamId) {
        return null;
    }
}
