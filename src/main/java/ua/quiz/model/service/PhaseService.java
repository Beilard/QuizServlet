package ua.quiz.model.service;

import ua.quiz.model.dto.Phase;

public interface PhaseService {
    void initiatePhase(Phase phase, Integer timePerQuestion);

    void finishPhase(Phase phase, String givenAnswer);

    void reviewPhasePositively(Phase phase);

    void useHint(Phase phase);
}
