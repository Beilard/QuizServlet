package ua.quiz.model.service.mapper;

import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.dto.Status;
import ua.quiz.model.entity.GameEntity;
import ua.quiz.model.entity.PhaseEntity;
import ua.quiz.model.entity.StatusEntity;

import java.util.List;
import java.util.stream.Collectors;

public class GameMapper {
    private final PhaseMapper phaseMapper = new PhaseMapper();

    public Game mapGameEntityToGame(GameEntity gameEntity) {
        if (gameEntity == null) {
            return null;
        }
        return Game.builder()
                .withId(gameEntity.getId())
                .withNumberOfQuestions(gameEntity.getNumberOfQuestions())
                .withTimePerQuestion(gameEntity.getTimePerQuestion())
                .withTeamId(gameEntity.getTeamId())
                .withCurrentPhase((gameEntity.getCurrentPhase()))
                .withStatus(Status.valueOf(gameEntity.getStatusEntity().name()))
                .withPhases(gameEntity.getPhaseEntities() == null ? null :
                        mapPhaseEntitiesToPhase(gameEntity.getPhaseEntities()))
                .build();
    }

    public GameEntity mapGameToGameEntity(Game game) {
        if (game == null) {
            return null;
        }

        return GameEntity.builder()
                .withId(game.getId())
                .withNumberOfQuestions(game.getNumberOfQuestions())
                .withTimePerQuestion(game.getTimePerQuestion())
                .withCurrentPhase(game.getCurrentPhase())
                .withTeamId(game.getTeamId())
                .withStatusEntity(StatusEntity.valueOf(game.getStatus().name()))
                .withPhaseEntities(mapPhaseToPhaseEntities(game.getPhases()))
                .build();
    }

    private List<Phase> mapPhaseEntitiesToPhase(List<PhaseEntity> phaseEntities) {
        return phaseEntities.stream()
                .map(phaseMapper::mapPhaseEntityToPhase)
                .collect(Collectors.toList());
    }

    private List<PhaseEntity> mapPhaseToPhaseEntities(List<Phase> phases) {
        return phases.stream()
                .map(phaseMapper::mapPhaseToPhaseEntity)
                .collect(Collectors.toList());
    }

}
