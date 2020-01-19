package ua.quiz.model.service;

import ua.quiz.model.dto.Phase;

import java.util.List;

public interface PhaseService {
    Phase initiatePhase(Phase phase, Integer timePerQuestion);

    Phase finishPhase(Phase phase, String givenAnswer);

    List<Phase> findAllPhasesByGameId(Long id);

    void extendDeadline(Long phaseId);

    void useHint(Long phaseId);
}
