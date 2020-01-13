package ua.quiz.model.service;

import ua.quiz.model.dto.Phase;

import java.util.List;

public interface PhaseService {
    Phase initiatePhase(Long gameId);

    void finishPhase(Long gameId);

    List<Phase> findAllPhasesByGameId(Long id);

    void extendDeadline(Long phaseId);

    void useHint(Long phaseId);
}
