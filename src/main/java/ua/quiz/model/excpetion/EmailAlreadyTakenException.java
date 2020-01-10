package ua.quiz.model.excpetion;

public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException() {
    }

    public EmailAlreadyTakenException(String message) {
        super(message);
    }

    public EmailAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyTakenException(Throwable cause) {
        super(cause);
    }
}
