package ua.quiz.model.service.validator;

public interface Validator<E> {
    void validate(E item);
}
