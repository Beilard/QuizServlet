package ua.quiz.model.service;

import ua.quiz.model.dto.Phase;

public interface PhaseService {
    Phase initiatePhase();

    void finishPhase();

    void extendDeadline();
}
