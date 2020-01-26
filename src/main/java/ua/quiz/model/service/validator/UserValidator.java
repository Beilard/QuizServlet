package ua.quiz.model.service.validator;

import org.apache.log4j.Logger;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.InvalidCredentialsException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<User> {
    private static final Logger LOGGER = Logger.getLogger(UserValidator.class);

    private static final String EMAIL_REGEX = "^(.+)@(.+)$";
    private static final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    @Override
    public void validate(User item) {
        if (item == null) {
            LOGGER.warn("Invalid user provided");
            throw new InvalidCredentialsException("Provided user is null");
        }
        validateEmail(item.getEmail());
        validatePassword(item.getPassword());
    }

    private void validateEmail(String email) {
        validateParameter(email, EMAIL_PATTERN, "Email not validated");
    }

    private void validatePassword(String password) {
        validateParameter(password, PASSWORD_PATTERN, "Password not validated");
    }

    private void validateParameter(String parameter, Pattern pattern, String message) {
        Matcher matcher = pattern.matcher(parameter);

        if (!matcher.find()) {
            LOGGER.warn(message);
            throw new InvalidCredentialsException(message);
        }
    }
}
