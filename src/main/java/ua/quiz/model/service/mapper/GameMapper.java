package ua.quiz.model.service.mapper;

import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Status;
import ua.quiz.model.entity.GameEntity;
import ua.quiz.model.entity.StatusEntity;

public class GameMapper {
    private final QuestionMapper questionMapper = new QuestionMapper();

    public Game mapGameEntityToGame(GameEntity gameEntity) {
        if (gameEntity == null) {
            return null;
        }
        return Game.builder()
                .withId(gameEntity.getId())
                .withNumberOfQuestions(gameEntity.getNumberOfQuestions())
                .withTimePerQuestion(gameEntity.getTimePerQuestion())
                .withTeamId(gameEntity.getTeamId())
                .withStatus(Status.valueOf(gameEntity.getStatusEntity().name()))
                .build();
    }

    public GameEntity mapGameToGameEntity(Game game) {
        return GameEntity.builder()
                .withId(game.getId())
                .withNumberOfQuestions(game.getNumberOfQuestions())
                .withTimePerQuestion(game.getTimePerQuestion())
                .withTeamId(game.getTeamId())
                .withStatusEntity(StatusEntity.valueOf(game.getStatus().name()))
                .build();
    }
}
