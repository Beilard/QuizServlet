package ua.quiz.model.dto;

import java.util.List;
import java.util.Objects;

public class Game {
    private final Long id;
    private final Integer numberOfQuestions;
    private final Integer timePerQuestion;
    private Integer currentPhase;
    private final Long teamId;
    private final Status status;
    private final List<Phase> phases;

    public Game(GameBuilder gameBuilder) {
        this.id = gameBuilder.id;
        this.numberOfQuestions = gameBuilder.numberOfQuestions;
        this.timePerQuestion = gameBuilder.timePerQuestion;
        this.status = gameBuilder.status;
        this.teamId = gameBuilder.teamId;
        this.currentPhase = gameBuilder.currentPhase;
        this.phases = gameBuilder.phases;
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public static GameBuilder builder(Game game) {
        return new GameBuilder(game);
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

    public Status getStatus() {
        return status;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Integer getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(Integer currentPhase) {
        this.currentPhase = currentPhase;
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public static class GameBuilder {
        private Long id;
        private Integer numberOfQuestions;
        private Integer timePerQuestion;
        private Integer currentPhase;
        private Long teamId;
        private Status status;
        private List<Phase> phases;

        private GameBuilder() {
        }

        public GameBuilder(Game game) {
            this.id = game.id;
            this.numberOfQuestions = game.numberOfQuestions;
            this.timePerQuestion = game.timePerQuestion;
            this.status = game.status;
            this.teamId = game.teamId;
            this.phases = game.phases;
            this.currentPhase = game.currentPhase;
        }

        public Game build() {
            return new Game(this);
        }

        public GameBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public GameBuilder withTimePerQuestion(Integer timePerQuestion) {
            this.timePerQuestion = timePerQuestion;
            return this;
        }

        public GameBuilder withCurrentPhase(Integer currentPhase) {
            this.currentPhase = currentPhase;
            return this;
        }

        public GameBuilder withPhases(List<Phase> phases) {
            this.phases = phases;
            return this;
        }

        public GameBuilder withNumberOfQuestions(Integer numberOfQuestions) {
            this.numberOfQuestions = numberOfQuestions;
            return this;
        }

        public GameBuilder withTeamId(Long teamId) {
            this.teamId = teamId;
            return this;
        }

        public GameBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Game)) {
            return false;
        }
        Game game = (Game) o;
        return id.equals(game.id) &&
                Objects.equals(numberOfQuestions, game.numberOfQuestions) &&
                Objects.equals(timePerQuestion, game.timePerQuestion) &&
                Objects.equals(currentPhase, game.currentPhase) &&
                Objects.equals(teamId, game.teamId) &&
                status == game.status &&
                Objects.equals(phases, game.phases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfQuestions, timePerQuestion, currentPhase, teamId, status, phases);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", numberOfQuestions=" + numberOfQuestions +
                ", timePerQuestion=" + timePerQuestion +
                ", currentPhase=" + currentPhase +
                ", teamId=" + teamId +
                ", status=" + status +
                ", phases=" + phases +
                '}';
    }
}
