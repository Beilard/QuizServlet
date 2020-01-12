package ua.quiz.model.entity;

import java.util.List;
import java.util.Objects;

public class GameEntity {
    private final Long id;
    private final Integer numberOfQuestions;
    private final Integer timePerQuestion;
    private final Long teamId;
    private final StatusEntity statusEntity;

    public GameEntity(GameEntityBuilder gameEntityBuilder) {
        this.id = gameEntityBuilder.id;
        this.numberOfQuestions = gameEntityBuilder.numberOfQuestions;
        this.timePerQuestion = gameEntityBuilder.timePerQuestion;
        this.statusEntity = gameEntityBuilder.statusEntity;
        this.teamId = gameEntityBuilder.teamId;
    }

    public static GameEntityBuilder builder() {
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

    public Long getTeamId() {
        return teamId;
    }

    public StatusEntity getStatusEntity() {
        return statusEntity;
    }

    public static class GameEntityBuilder {
        private Long id;
        private Integer numberOfQuestions;
        private Integer timePerQuestion;
        private Long teamId;
        private StatusEntity statusEntity;

        private GameEntityBuilder() {
        }

        public GameEntity build() {
            return new GameEntity(this);
        }

        public GameEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public GameEntityBuilder withTimePerQuestion(Integer timePerQuestion) {
            this.timePerQuestion = timePerQuestion;
            return this;
        }

        public GameEntityBuilder withNumberOfQuestions(Integer numberOfQuestions) {
            this.numberOfQuestions = numberOfQuestions;
            return this;
        }

        public GameEntityBuilder withTeamId(Long teamId) {
            this.teamId = teamId;
            return this;
        }

        public GameEntityBuilder withStatusEntity(StatusEntity statusEntity) {
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
        GameEntity that = (GameEntity) o;
        return id.equals(that.id) &&
                Objects.equals(numberOfQuestions, that.numberOfQuestions) &&
                Objects.equals(timePerQuestion, that.timePerQuestion) &&
                Objects.equals(teamId, that.teamId) &&
                statusEntity == that.statusEntity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfQuestions, timePerQuestion, teamId, statusEntity);
    }

    @Override
    public String toString() {
        return "GameEntity{" +
                "id=" + id +
                ", numberOfQuestions=" + numberOfQuestions +
                ", timePerQuestion=" + timePerQuestion +
                ", teamId=" + teamId +
                ", statusEntity=" + statusEntity +
                '}';
    }
}
