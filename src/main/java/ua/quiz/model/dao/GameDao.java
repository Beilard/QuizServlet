package ua.quiz.model.dao;

import ua.quiz.model.entity.GameEntity;

import java.util.List;

public interface GameDao extends CrudDao<GameEntity, Long>{
    Long saveAndReturnId(GameEntity gameEntity);
    List<GameEntity> findAllByTeamId(Long teamId, Long startFrom, Long countRow);
    Long countAllByTeamId(Long teamId);
}
