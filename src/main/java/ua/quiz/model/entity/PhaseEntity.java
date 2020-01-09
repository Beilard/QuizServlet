package ua.quiz.model.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PhaseEntity {
    private final Long id;
    private final Boolean hintUsed;
    private final LocalDate startTime;
    private final LocalDate endTime;
    private final LocalDate deadline;

    public PhaseEntity(PhaseEntityBuilder phaseEntityBuilder) {
        this.id = phaseEntityBuilder.id;
        this.body = phaseEntityBuilder.body;
        this.answers = phaseEntityBuilder.answers;
        this.hint = phaseEntityBuilder.hint;
        this.startTime = phaseEntityBuilder.startTime;
        this.endTime = phaseEntityBuilder.endTime;
    }

    public static PhaseEntityBuilder builder(){
        return new PhaseEntityBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getHint() {
        return hint;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public static class PhaseEntityBuilder {
        private Long id;
        private String body;
        private List<String> answers;
        private String hint;
        private LocalDate startTime;
        private LocalDate endTime;

        private PhaseEntityBuilder() {
        }

        public PhaseEntity build(){
            return new PhaseEntity(this);
        }

        public PhaseEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public PhaseEntityBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public PhaseEntityBuilder withAnswers(List<String> answers) {
            this.answers = answers;
            return this;
        }

        public PhaseEntityBuilder withHint(String hint) {
            this.hint = hint;
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhaseEntity)) {
            return false;
        }
        PhaseEntity phaseEntity = (PhaseEntity) o;
        return id.equals(phaseEntity.id) &&
                Objects.equals(body, phaseEntity.body) &&
                Objects.equals(answers, phaseEntity.answers) &&
                Objects.equals(hint, phaseEntity.hint) &&
                Objects.equals(startTime, phaseEntity.startTime) &&
                Objects.equals(endTime, phaseEntity.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body, answers, hint, startTime, endTime);
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", body='" + body + '\'' +
                ", answers=" + answers +
                ", hint='" + hint + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
