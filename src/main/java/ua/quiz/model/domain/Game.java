package ua.quiz.model.domain;

import java.util.List;
import java.util.Objects;

public class Game {
    private final Long id;
    private final List<Question> questions;
    private final Integer numberOfQuestions;
    private final Integer timePerQuestion;
    private final Long teamId;
    private final Status status;

    public Game(GameBuilder gameBuilder) {
        this.id = gameBuilder.id;
        this.numberOfQuestions = gameBuilder.numberOfQuestions;
        this.timePerQuestion = gameBuilder.timePerQuestion;
        this.questions = gameBuilder.questions;
        this.status = gameBuilder.status;
        this.teamId = gameBuilder.teamId;
    }

    public static GameBuilder builder(){
        return new GameBuilder();
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

    public List<Question> getQuestions() {
        return questions;
    }

    public Status getStatus() {
        return status;
    }

    public Long getTeamId() {
        return teamId;
    }

    public static class GameBuilder {
        private Long id;
        private List<Question> questions;
        private Integer numberOfQuestions;
        private Integer timePerQuestion;
        private Long teamId;
        private Status status;

        private GameBuilder() {
        }

        public Game build(){
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

        public GameBuilder withQuestions(List<Question> questions) {
            this.questions = questions;
            return this;
        }

        public GameBuilder withNumberOfQuestions(Integer numberOfQuestions) {
            this.numberOfQuestions = numberOfQuestions;
            return this;
        }

        public GameBuilder withTeam(Long teamId) {
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
                Objects.equals(questions, game.questions) &&
                status == game.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfQuestions, timePerQuestion, questions, status);
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", numberOfQuestion=" + numberOfQuestions +
                ", timePerQuestion=" + timePerQuestion +
                ", questions=" + questions +
                ", status=" + status +
                '}';
    }
}
