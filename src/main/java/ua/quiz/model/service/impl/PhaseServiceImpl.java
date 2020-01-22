package ua.quiz.model.service.impl;

import org.apache.log4j.Logger;
import ua.quiz.model.dao.PhaseDao;
import ua.quiz.model.dao.QuestionDao;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.service.PhaseService;
import ua.quiz.model.service.mapper.PhaseMapper;
import ua.quiz.model.service.mapper.QuestionMapper;

import java.time.LocalDateTime;
import java.util.List;

public class PhaseServiceImpl implements PhaseService {
    private static final Logger LOGGER = Logger.getLogger(PhaseServiceImpl.class);
    private static final int DEADLINE_EXTENSION = 60;

    private final PhaseDao phaseDao;
    private final QuestionDao questionDao;
    private final PhaseMapper phaseMapper;
    private final QuestionMapper questionMapper;

    public PhaseServiceImpl(PhaseDao phaseDao, QuestionDao questionDao,
                            PhaseMapper phaseMapper, QuestionMapper questionMapper) {
        this.phaseDao = phaseDao;
        this.questionDao = questionDao;
        this.phaseMapper = phaseMapper;
        this.questionMapper = questionMapper;
    }

    @Override
    public Phase initiatePhase(Phase phase, Integer timePerQuestion) {
        if (phase == null) {
            LOGGER.warn("Passed phase is null");
            throw  new IllegalArgumentException("Passed phase is null");
        }
        Phase initiatedPhase = setDeadlines(phase, timePerQuestion);

        phaseDao.update(phaseMapper.mapPhaseToPhaseEntity(initiatedPhase));

        return initiatedPhase;
    }

    @Override
    public Phase finishPhase(Phase phase, String givenAnswer) {
        if (phase == null) {
            LOGGER.warn("Passed phase is null");
            throw  new IllegalArgumentException("Passed phase is null");
        }
        final Phase finishedPhase = setEndTimeAndAnswer(phase, givenAnswer);
        phaseDao.update(phaseMapper.mapPhaseToPhaseEntity(finishedPhase));

        return finishedPhase;
    }

    @Override
    public Phase reviewPhasePositively(Phase phase) {
        if (phase == null) {
            LOGGER.warn("Passed phase is null");
            throw  new IllegalArgumentException("Passed phase is null");
        }

        return changeToCorrect(phase);
    }

    @Override
    public Phase useHint(Phase phase) {
        if (phase == null) {
            LOGGER.warn("Passed phase is null");
            throw new IllegalArgumentException("Passed phase is null");
        }
        Phase phaseWithHint = enableHint(phase);
        phaseDao.update(phaseMapper.mapPhaseToPhaseEntity(phaseWithHint));

        return phaseWithHint;
    }

    private Phase setDeadlines(Phase phase, Integer timePerQuestion) {
        return Phase.builder(phase)
                .withStartTime(LocalDateTime.now())
                .withDeadline(LocalDateTime.now().plusSeconds(timePerQuestion))
                .withHintUsed(false)
                .withCorrect(false)
                .build();
    }

    private Phase setEndTimeAndAnswer(Phase phase, String givenAnswer) {
        return Phase.builder(phase)
                .withEndTime(LocalDateTime.now())
                .withGivenAnswer(givenAnswer)
                .build();
    }

    private Phase enableHint(Phase phase) {
        return Phase.builder(phase)
                .withHintUsed(true)
                .build();
    }

    private Phase changeToCorrect(Phase phase) {
        return Phase.builder(phase)
                .withCorrect(true)
                .build();
    }
}
