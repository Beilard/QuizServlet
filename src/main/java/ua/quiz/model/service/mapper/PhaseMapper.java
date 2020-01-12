package ua.quiz.model.service.mapper;


import ua.quiz.model.dto.Phase;
import ua.quiz.model.entity.PhaseEntity;

public class PhaseMapper {
    private final QuestionMapper questionMapper = new QuestionMapper();

    public Phase mapQuestionEntityToQuestion(PhaseEntity phaseEntity) {
        if (phaseEntity == null) {
            return null;
        }
        return Phase.builder()
                .withId(phaseEntity.getId())
                .withQuestion(questionMapper.mapQuestionEntityToQuestion(phaseEntity.getQuestion()))
                .withStartTime(phaseEntity.getStartTime())
                .withEndTime(phaseEntity.getStartTime())
                .withDeadline(phaseEntity.getDeadline())
                .withHintUsed(phaseEntity.getHintUsed())
                .withCorrect(phaseEntity.getCorrect())
                .withGivenAnswer(phaseEntity.getGivenAnswer())
                .withGameId(phaseEntity.getGameId())
                .build();
    }

    public PhaseEntity mapQuestionToQuestionEntity(Phase phase) {
        return PhaseEntity.builder()
                .withId(phase.getId())
                .withQuestion(questionMapper.mapQuestionToQuestionEntity(phase.getQuestion()))
                .withStartTime(phase.getStartTime())
                .withEndTime(phase.getStartTime())
                .withDeadline(phase.getDeadline())
                .withHintUsed(phase.getHintUsed())
                .withCorrect(phase.getCorrect())
                .withGivenAnswer(phase.getGivenAnswer())
                .withGameId(phase.getGameId())
                .build();
    }
}
