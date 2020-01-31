package ua.quiz.model.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class Phase {
    private final Long id;
    private final Question question;
    private final Boolean hintUsed;
    private final Boolean isCorrect;
    private final String givenAnswer;
    private final Long gameId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final LocalDateTime deadline;

    public Phase(PhaseBuilder phaseBuilder) {
        this.id = phaseBuilder.id;
        this.question = phaseBuilder.question;
        this.startTime = phaseBuilder.startTime;
        this.endTime = phaseBuilder.endTime;
        this.deadline = phaseBuilder.deadline;
        this.hintUsed = phaseBuilder.hintUsed;
        this.isCorrect = phaseBuilder.isCorrect;
        this.givenAnswer = phaseBuilder.givenAnswer;
        this.gameId = phaseBuilder.gameId;
    }

    public static PhaseBuilder builder() {
        return new PhaseBuilder();
    }

    public static PhaseBuilder builder(Phase phase) {
        return new PhaseBuilder(phase);
    }

    public Long getId() {
        return id;
    }

    public Boolean getHintUsed() {
        return hintUsed;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Question getQuestion() {
        return question;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public Long getGameId() {
        return gameId;
    }

    public static class PhaseBuilder {
        private Long id;
        private Question question;
        private Boolean hintUsed;
        private Boolean isCorrect;
        private String givenAnswer;
        private Long gameId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private LocalDateTime deadline;

        private PhaseBuilder() {
        }

        public PhaseBuilder(Phase phase) {
            this.id = phase.id;
            this.question = phase.question;
            this.startTime = phase.startTime;
            this.endTime = phase.endTime;
            this.deadline = phase.deadline;
            this.hintUsed = phase.hintUsed;
            this.isCorrect = phase.isCorrect;
            this.givenAnswer = phase.givenAnswer;
            this.gameId = phase.gameId;
        }

        public Phase build() {
            return new Phase(this);
        }

        public PhaseBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public PhaseBuilder withQuestion(Question question) {
            this.question = question;
            return this;
        }

        public PhaseBuilder withCorrect(Boolean correct) {
            isCorrect = correct;
            return this;
        }

        public PhaseBuilder withHintUsed(Boolean hintUsed) {
            this.hintUsed = hintUsed;
            return this;
        }

        public PhaseBuilder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public PhaseBuilder withDeadline(LocalDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public PhaseBuilder withGivenAnswer(String givenAnswer) {
            this.givenAnswer = givenAnswer;
            return this;
        }

        public PhaseBuilder withGameId(Long gameId) {
            this.gameId = gameId;
            return this;
        }

        public PhaseBuilder withEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phase)) {
            return false;
        }
        Phase phase = (Phase) o;
        return id.equals(phase.id) &&
                Objects.equals(question, phase.question) &&
                Objects.equals(hintUsed, phase.hintUsed) &&
                Objects.equals(isCorrect, phase.isCorrect) &&
                Objects.equals(givenAnswer, phase.givenAnswer) &&
                Objects.equals(gameId, phase.gameId) &&
                Objects.equals(startTime, phase.startTime) &&
                Objects.equals(endTime, phase.endTime) &&
                Objects.equals(deadline, phase.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, hintUsed, isCorrect, givenAnswer, gameId, startTime, endTime, deadline);
    }

    @Override
    public String toString() {
        return "Phase{" +
                "id=" + id +
                ", question=" + question +
                ", hintUsed=" + hintUsed +
                ", isCorrect=" + isCorrect +
                ", givenAnswer='" + givenAnswer + '\'' +
                ", gameId=" + gameId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", deadline=" + deadline +
                '}';
    }
}
