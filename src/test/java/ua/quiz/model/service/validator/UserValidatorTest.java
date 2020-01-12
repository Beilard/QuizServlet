package ua.quiz.model.service.validator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.quiz.model.dto.User;
import ua.quiz.model.excpetion.InvalidCredentialsExcpetion;

public class UserValidatorTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final UserValidator userValidator = new UserValidator();

    @Test
    public void validateShouldThrowInvalidCredentialsDueToNullUser() {
        expectedException.expect(InvalidCredentialsExcpetion.class);
        expectedException.expectMessage("Provided user is null");

        userValidator.validate(null);
    }

    @Test
    public void validateShouldThrowInvalidCredentialsDueToEmail() {
        expectedException.expect(InvalidCredentialsExcpetion.class);
        expectedException.expectMessage("Email not validated");

        userValidator.validate(User.builder()
                .withEmail("123")
                .build());
    }

    @Test
    public void validateShouldThrowInvalidCredentialsDueToPassword() {
        expectedException.expect(InvalidCredentialsExcpetion.class);
        expectedException.expectMessage("Password not validated");

        userValidator.validate(User.builder()
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