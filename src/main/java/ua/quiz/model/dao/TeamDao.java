package ua.quiz.model.dao;

import ua.quiz.model.entity.TeamEntity;

import java.util.List;
import java.util.Optional;

public interface TeamDao extends CrudDao<TeamEntity, Long>{
    Optional<TeamEntity> findByTeamName(String teamName);
}
