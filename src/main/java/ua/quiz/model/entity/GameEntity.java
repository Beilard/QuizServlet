package ua.quiz.model.entity;

import java.util.List;
import java.util.Objects;

public class GameEntity {
    private final Long id;
    private final Integer numberOfQuestions;
    private final Integer timePerQuestion;
    private final List<QuestionEntity> questionEntities;
    private final StatusEntity statusEntity;

    public GameEntity(GameEntityBuilder gameEntityBuilder) {
        this.id = gameEntityBuilder.id;
        this.numberOfQuestions = gameEntityBuilder.numberOfQuestions;
        this.timePerQuestion = gameEntityBuilder.timePerQuestion;
        this.questionEntities = gameEntityBuilder.questionEntities;
        this.statusEntity = gameEntityBuilder.statusEntity;
    }

    public static GameEntityBuilder builder(){
        return new GameEntityBuilder();
    }

    public Long getId() {
        return id;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public Integer getTimePerQuestion() {
        return timePerQuestion;
    }

    public List<QuestionEntity> getQuestionEntities() {
        return questionEntities;
    }

    public StatusEntity getStatusEntity() {
        return statusEntity;
    }

    public static class GameEntityBuilder {
        private Long id;
        private Integer numberOfQuestions;
        private Integer timePerQuestion;
        private List<QuestionEntity> questionEntities;
        private StatusEntity statusEntity;

        private GameEntityBuilder() {
        }

        public GameEntity build(){
            return new GameEntity(this);
        }

        public GameEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public GameEntityBuilder withNumberOfQuestion(Integer numberOfQuestion) {
            this.numberOfQuestions = numberOfQuestion;
            return this;
        }

        public GameEntityBuilder withTimePerQuestion(Integer timePerQuestion) {
            this.timePerQuestion = timePerQuestion;
            return this;
        }

        public GameEntityBuilder withQuestions(List<QuestionEntity> questionEntities) {
            this.questionEntities = questionEntities;
            return this;
        }

        public GameEntityBuilder withStatus(StatusEntity statusEntity) {
            this.statusEntity = statusEntity;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameEntity)) {
            return false;
        }
        GameEntity gameEntity = (GameEntity) o;
        return id.equals(gameEntity.id) &&
                Objects.equals(numberOfQuestions, gameEntity.numberOfQuestions) &&
                Objects.equals(timePerQuestion, gameEntity.timePerQuestion) &&
                Objects.equals(questionEntities, gameEntity.questionEntities) &&
                statusEntity == gameEntity.statusEntity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfQuestions, timePerQuestion, questionEntities, statusEntity);
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", numberOfQuestion=" + numberOfQuestions +
                ", timePerQuestion=" + timePerQuestion +
                ", questions=" + questionEntities +
                ", status=" + statusEntity +
                '}';
    }
}
