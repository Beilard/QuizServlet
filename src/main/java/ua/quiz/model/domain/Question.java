package ua.quiz.model.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Question {
    private final Long id;
    private final String body;
    private final List<String> answers;
    private final String hint;
    private final LocalDate startTime;
    private final LocalDate endTime;

    public Question(QuestionBuilder questionBuilder) {
        this.id = questionBuilder.id;
        this.body = questionBuilder.body;
        this.answers = questionBuilder.answers;
        this.hint = questionBuilder.hint;
        this.startTime = questionBuilder.startTime;
        this.endTime = questionBuilder.endTime;
    }

    public static QuestionBuilder builder(){
        return new QuestionBuilder();
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

    public static class QuestionBuilder {
        private Long id;
        private String body;
        private List<String> answers;
        private String hint;
        private LocalDate startTime;
        private LocalDate endTime;

        private QuestionBuilder() {
        }

        public Question build(){
            return new Question(this);
        }

        public QuestionBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public QuestionBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public QuestionBuilder withAnswers(List<String> answers) {
            this.answers = answers;
            return this;
        }

        public QuestionBuilder withHint(String hint) {
            this.hint = hint;
            return this;
        }

        public QuestionBuilder withStartTime(LocalDate startTime) {
            this.startTime = startTime;
            return this;
        }

        public QuestionBuilder withEndTime(LocalDate endTime) {
            this.endTime = endTime;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        Question question = (Question) o;
        return id.equals(question.id) &&
                Objects.equals(body, question.body) &&
                Objects.equals(answers, question.answers) &&
                Objects.equals(hint, question.hint) &&
                Objects.equals(startTime, question.startTime) &&
                Objects.equals(endTime, question.endTime);
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
