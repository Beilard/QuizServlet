package ua.quiz.model.entity;

import ua.quiz.model.domain.Question;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PhaseEntity {
    private final Long id;
    private final Question question;
    private final Boolean hintUsed;
    private final Boolean isCorrect;
    private final LocalDate startTime;
    private final LocalDate endTime;
    private final LocalDate deadline;

    public PhaseEntity(PhaseEntityBuilder phaseBuilder) {
        this.id = phaseBuilder.id;
        this.question = phaseBuilder.question;
        this.startTime = phaseBuilder.startTime;
        this.endTime = phaseBuilder.endTime;
        this.deadline = phaseBuilder.deadline;
        this.hintUsed = phaseBuilder.hintUsed;
        this.isCorrect = phaseBuilder.isCorrect;
    }

    public static PhaseEntityBuilder builder() {
        return new PhaseEntityBuilder();
    }

    public Long getId() {
        return id;
    }

    public Boolean getHintUsed() {
        return hintUsed;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public Question getQuestion() {
        return question;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public static class PhaseEntityBuilder {
        private Long id;
        private Question question;
        private Boolean hintUsed;
        private Boolean isCorrect;
        private LocalDate startTime;
        private LocalDate endTime;
        private LocalDate deadline;

        private PhaseEntityBuilder() {
        }

        public PhaseEntity build(){
            return new PhaseEntity(this);
        }

        public PhaseEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public PhaseEntityBuilder withQuestion(Question question) {
            this.question = question;
            return this;
        }

        public PhaseEntityBuilder withHintUsed(Boolean hintUsed) {
            this.hintUsed = hintUsed;
            return this;
        }

        public PhaseEntityBuilder withCorrect(Boolean correct) {
            isCorrect = correct;
            return this;
        }

        public PhaseEntityBuilder withStartTime(LocalDate startTime) {
            this.startTime = startTime;
            return this;
        }

        public PhaseEntityBuilder withEndTime(LocalDate endTime) {
            this.endTime = endTime;
            return this;
        }

        public PhaseEntityBuilder withDeadline(LocalDate deadline) {
            this.deadline = deadline;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhaseEntity)) return false;
        PhaseEntity that = (PhaseEntity) o;
        return id.equals(that.id) &&
                Objects.equals(question, that.question) &&
                Objects.equals(hintUsed, that.hintUsed) &&
                Objects.equals(isCorrect, that.isCorrect) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(deadline, that.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, hintUsed, isCorrect, startTime, endTime, deadline);
    }

    @Override
    public String toString() {
        return "PhaseEntity{" +
                "id=" + id +
                ", question=" + question +
                ", hintUsed=" + hintUsed +
                ", isCorrect=" + isCorrect +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", deadline=" + deadline +
                '}';
    }
}
