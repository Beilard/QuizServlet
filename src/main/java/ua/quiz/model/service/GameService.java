package ua.quiz.model.service;

import ua.quiz.model.dto.Game;

import java.util.List;

public interface GameService {
    Game startGame(Long teamId, int numberOfQuestions, int timePerQuestion);

    void finishGame(Game game);

    Game reviewGame(Game game);

    Game findById(Long id);

    List<Game> findAll();

    List<Game> findAllByTeamId(Long teamId);
}
