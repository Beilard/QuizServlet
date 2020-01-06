package ua.quiz.model.excpetion;

import java.sql.SQLException;

public class DataBaseRuntimeException extends RuntimeException {
    public DataBaseRuntimeException() {
    }

    public DataBaseRuntimeException(String message) {
        super(message);
    }

    public DataBaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseRuntimeException(Throwable cause) {
        super(cause);
    }
}
