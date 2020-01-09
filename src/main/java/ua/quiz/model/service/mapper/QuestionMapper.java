//package ua.quiz.model.service.mapper;
//
//import ua.quiz.model.domain.Phase;
//import ua.quiz.model.entity.PhaseEntity;
//
//public class QuestionMapper {
//    public Phase mapQuestionEntityToQuestion(PhaseEntity phaseEntity) {
//        if (phaseEntity == null) {
//            return null;
//        }
//        return Phase.builder()
//                .withId(phaseEntity.getId())
//                .withBody(phaseEntity.getBody())
//                .withAnswers(phaseEntity.getAnswers())
//                .withStartTime(phaseEntity.getStartTime())
//                .withEndTime(phaseEntity.getStartTime())
//                .withHint(phaseEntity.getHint())
//                .build();
//    }
//
//    public PhaseEntity mapQuestionToQuestionEntity(Phase phase) {
//        return PhaseEntity.builder()
//                .withId(phase.getId())
//                .withBody(phase.getBody())
//                .withAnswers(phase.getAnswers())
//                .withStartTime(phase.getStartTime())
//                .withEndTime(phase.getStartTime())
//                .withHint(phase.getHint())
//                .build();
//    }
//}
