package ua.quiz.model.service.impl;

import org.apache.log4j.Logger;
import ua.quiz.model.dao.PhaseDao;
import ua.quiz.model.dao.QuestionDao;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.service.PhaseService;
import ua.quiz.model.service.mapper.PhaseMapper;
import ua.quiz.model.service.mapper.QuestionMapper;

import java.util.List;

public class PhaseServiceImpl implements PhaseService {
    private static final Logger LOGGER = Logger.getLogger(PhaseServiceImpl.class);

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
    public Phase initiatePhase(Long gameId) {
        if (gameId == null) {
            LOGGER.warn("Passed gameId is null");
            throw  new IllegalArgumentException("Passed gameId is null");
        }


        return null;
    }

    @Override
    public void finishPhase(Long gameId) {

    }

    @Override
    public List<Phase> findAllPhasesByGameId(Long id) {
        return null;
    }

    @Override
    public void extendDeadline(Long phaseId) {

    }

    @Override
    public void useHint(Long phaseId) {

    }
}
