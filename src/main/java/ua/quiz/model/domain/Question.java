package ua.quiz.model.domain;

import java.util.List;
import java.util.Objects;

public class Question {
    private final Long id;
    private final String body;
    private final List<String> answers;
    private final String correctAnswer;
    private final String hint;

    public Question(QuestionBuilder questionBuilder) {
        this.id = questionBuilder.id;
        this.body = questionBuilder.body;
        this.answers = questionBuilder.answers;
        this.hint = questionBuilder.hint;
        this.correctAnswer = questionBuilder.correctAnswer;
    }

    public static QuestionBuilder builder() {
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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public static class QuestionBuilder {
        private Long id;
        private String body;
        private List<String> answers;
        private String correctAnswer;
        private String hint;

        private QuestionBuilder() {
        }

        public Question build() {
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

        public QuestionBuilder withCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return id.equals(question.id) &&
                Objects.equals(body, question.body) &&
                Objects.equals(answers, question.answers) &&
                Objects.equals(correctAnswer, question.correctAnswer) &&
                Objects.equals(hint, question.hint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body, answers, correctAnswer, hint);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", answers=" + answers +
                ", hint='" + hint + '\'' +
                '}';
    }
}
