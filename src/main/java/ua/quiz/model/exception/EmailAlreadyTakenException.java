package ua.quiz.model.exception;

public class EmailAlreadyTakenException extends RuntimeException {

    public EmailAlreadyTakenException(String message) {
        super(message);
    }

}
