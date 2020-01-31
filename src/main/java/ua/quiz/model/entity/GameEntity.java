package ua.quiz.model.entity;

import java.util.List;
import java.util.Objects;

public class GameEntity {
    private final Long id;
    private final Integer numberOfQuestions;
    private final Integer timePerQuestion;
    private final Integer currentPhase;
    private final Long teamId;
    private final StatusEntity statusEntity;
    private final List<PhaseEntity> phaseEntities;

    public GameEntity(GameEntityBuilder gameEntityBuilder) {
        this.id = gameEntityBuilder.id;
        this.numberOfQuestions = gameEntityBuilder.numberOfQuestions;
        this.timePerQuestion = gameEntityBuilder.timePerQuestion;
        this.statusEntity = gameEntityBuilder.statusEntity;
        this.teamId = gameEntityBuilder.teamId;
        this.phaseEntities = gameEntityBuilder.phaseEntities;
        this.currentPhase = gameEntityBuilder.currentPhase;
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

    public Integer getCurrentPhase() {
        return currentPhase;
    }

    public Long getTeamId() {
        return teamId;
    }

    public StatusEntity getStatusEntity() {
        return statusEntity;
    }

    public List<PhaseEntity> getPhaseEntities() {
        return phaseEntities;
    }

    public static class GameEntityBuilder {
        private Long id;
        private Integer numberOfQuestions;
        private Integer timePerQuestion;
        private Integer currentPhase;
        private Long teamId;
        private StatusEntity statusEntity;
        private List<PhaseEntity> phaseEntities;

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

        public GameEntityBuilder withCurrentPhase(Integer currentPhase) {
            this.currentPhase = currentPhase;
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

        public GameEntityBuilder withPhaseEntities(List<PhaseEntity> phaseEntities) {
            this.phaseEntities = phaseEntities;
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
                Objects.equals(currentPhase, that.currentPhase) &&
                Objects.equals(teamId, that.teamId) &&
                statusEntity == that.statusEntity &&
                Objects.equals(phaseEntities, that.phaseEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfQuestions, timePerQuestion, currentPhase, teamId, statusEntity, phaseEntities);
    }

    @Override
    public String toString() {
        return "GameEntity{" +
                "id=" + id +
                ", numberOfQuestions=" + numberOfQuestions +
                ", timePerQuestion=" + timePerQuestion +
                ", currentPhase=" + currentPhase +
                ", teamId=" + teamId +
                ", statusEntity=" + statusEntity +
                ", phaseEntities=" + phaseEntities +
                '}';
    }
}
