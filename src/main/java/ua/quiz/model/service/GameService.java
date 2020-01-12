package ua.quiz.model.service;

import ua.quiz.model.dto.Game;

public interface GameService {
    Game createGame(Long teamId, int numberOfQuestions, int timePerQuestion);
}
