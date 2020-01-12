package ua.quiz.model.entity;

import java.util.Objects;

public class QuestionEntity {
    private final Long id;
    private final String body;
    private final String correctAnswer;
    private final String hint;

    public QuestionEntity(QuestionEntityBuilder questionEntityBuilder) {
        this.id = questionEntityBuilder.id;
        this.body = questionEntityBuilder.body;
        this.hint = questionEntityBuilder.hint;
        this.correctAnswer = questionEntityBuilder.correctAnswer;
    }

    public static QuestionEntityBuilder builder() {
        return new QuestionEntityBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getHint() {
        return hint;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public static class QuestionEntityBuilder {
        private Long id;
        private String body;
        private String correctAnswer;
        private String hint;

        private QuestionEntityBuilder() {
        }

        public QuestionEntity build() {
            return new QuestionEntity(this);
        }

        public QuestionEntityBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public QuestionEntityBuilder withBody(String body) {
            this.body = body;
            return this;
        }

        public QuestionEntityBuilder withCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
            return this;
        }

        public QuestionEntityBuilder withHint(String hint) {
            this.hint = hint;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionEntity)) {
            return false;
        }
        QuestionEntity that = (QuestionEntity) o;
        return id.equals(that.id) &&
                Objects.equals(body, that.body) &&
                Objects.equals(correctAnswer, that.correctAnswer) &&
                Objects.equals(hint, that.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body, correctAnswer, hint);
    }

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", hint='" + hint + '\'' +
                '}';
    }
}
