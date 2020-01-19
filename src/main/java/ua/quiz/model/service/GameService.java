package ua.quiz.model.service;

import ua.quiz.model.dto.Game;

import java.util.List;

public interface GameService {
    Game startGame(Long teamId, int numberOfQuestions, int timePerQuestion);

    void finishGame(Game game);

    void reviewGame(Game game);

    void updateGame(Game game);

    Game findById(Long id);

    List<Game> findAll(Long page, Long rowCount);

    List<Game> findAllByTeamId(Long teamId);

    Long countAllEntries();
}
