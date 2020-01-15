package ua.quiz.model.dao;

import ua.quiz.model.entity.GameEntity;

public interface GameDao extends CrudDao<GameEntity, Long>{
    Long saveAndReturnId(GameEntity gameEntity);
}
