package ua.quiz.model.dao;

import ua.quiz.model.entity.PhaseEntity;

import java.util.List;

public interface PhaseDao extends CrudDao<PhaseEntity, Long> {
    List<PhaseEntity> findPhasesByGameId(Long gameId);
}
