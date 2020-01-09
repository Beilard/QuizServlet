//package ua.quiz.model.service.mapper;
//
//import ua.quiz.model.domain.Game;
//import ua.quiz.model.domain.Phase;
//import ua.quiz.model.domain.Status;
//import ua.quiz.model.entity.GameEntity;
//import ua.quiz.model.entity.PhaseEntity;
//import ua.quiz.model.entity.StatusEntity;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class GameMapper {
//    private final QuestionMapper questionMapper = new QuestionMapper();
//
//    public Game mapGameEntityToGame(GameEntity gameEntity) {
//        if (gameEntity == null) {
//            return null;
//        }
//        return Game.builder()
//                .withId(gameEntity.getId())
//                .withNumberOfQuestion(gameEntity.getNumberOfQuestions())
//                .withQuestions(questionEntityConverter(gameEntity.getQuestionEntities()))
//                .withTimePerQuestion(gameEntity.getTimePerQuestion())
//                .withStatus(Status.valueOf(gameEntity.getStatusEntity().name()))
//                .build();
//    }
//
//    public GameEntity mapGameToGameEntity(Game game) {
//        return GameEntity.builder()
//                .withId(game.getId())
//                .withNumberOfQuestion(game.getNumberOfQuestions())
//                .withQuestions(questionConverter(game.getPhases()))
//                .withTimePerQuestion(game.getTimePerQuestion())
//                .withStatus(StatusEntity.valueOf(game.getStatus().name()))
//                .build();
//    }
//
//    private List<Phase> questionEntityConverter(List<PhaseEntity> questionEntities) {
//        return questionEntities.stream()
//                .map(questionMapper::mapQuestionEntityToQuestion)
//                .collect(Collectors.toList());
//    }
//
//    private List<PhaseEntity> questionConverter(List<Phase> phases) {
//        return phases.stream()
//                .map(questionMapper::mapQuestionToQuestionEntity)
//                .collect(Collectors.toList());
//    }
//}
