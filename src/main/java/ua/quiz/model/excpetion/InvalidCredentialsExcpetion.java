package ua.quiz.model.excpetion;

public class InvalidCredentialsExcpetion extends RuntimeException {
    public InvalidCredentialsExcpetion() {
    }

    public InvalidCredentialsExcpetion(String message) {
        super(message);
    }

    public InvalidCredentialsExcpetion(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCredentialsExcpetion(Throwable cause) {
        super(cause);
    }
}
