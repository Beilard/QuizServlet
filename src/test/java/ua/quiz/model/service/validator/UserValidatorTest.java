package ua.quiz.model.service.validator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.InvalidCredentialsException;

public class UserValidatorTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final UserValidator userValidator = new UserValidator();

    @Test
    public void validateShouldThrowInvalidCredentialsDueToNullUser() {
        expectedException.expect(InvalidCredentialsException.class);
        expectedException.expectMessage("Provided user is null");

        userValidator.validate(null);
    }

    @Test
    public void validateShouldThrowInvalidCredentialsDueToEmail() {
        expectedException.expect(InvalidCredentialsException.class);
        expectedException.expectMessage("Email not validated");

        userValidator.validate(User.builder()
                .withEmail("123")
                .build());
    }

    @Test
    public void validateShouldThrowInvalidCredentialsDueToPassword() {
        expectedException.expect(InvalidCredentialsException.class);
        expectedException.expectMessage("Password not validated");

        userValidator.validate(User.builder()
                .withEmail("Vova@ukr.net")
                .withPassword("123")
                .build());
    }

    @Test
    public void validateShouldBehaveCorrectly() {
        userValidator.validate(User.builder()
                .withName("Ivan")
                .withSurname("Popov")
                .withId(1L)
                .withEmail("Ivan@mail.com")
                .withPassword("Qwerty123#")
                .build());
    }
}