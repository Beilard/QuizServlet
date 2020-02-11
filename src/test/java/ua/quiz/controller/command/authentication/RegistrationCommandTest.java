package ua.quiz.controller.command.authentication;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.exception.EmailAlreadyTakenException;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationCommandTest {

    @InjectMocks
    private RegistrationCommand registrationCommand;
    @Mock
    private UserService userService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @After
    public void resetMocks() {
        reset(request, response, userService, session);
    }

    @Test
    public void executeShouldReturnToRegistrationDueToPasswordMismatch() {
        when(request.getParameter("password")).thenReturn("password1");
        when(request.getParameter("confirmPassword")).thenReturn("password2");

        final String actual = registrationCommand.execute(request, response);
        final String expected = "/game?command=registrationForm";

        assertThat(actual, Is.is(expected));
    }

    @Test
    public void executeShouldReturnToRegistrationDueToEmailTaken() {
        when(request.getParameter("password")).thenReturn("password1");
        when(request.getParameter("confirmPassword")).thenReturn("password1");
        doThrow(EmailAlreadyTakenException.class).when(userService).register(any());

        final String actual = registrationCommand.execute(request, response);
        final String expected = "/game?command=registrationForm";
        assertThat(actual, Is.is(expected));
        verify(request).setAttribute("registrationMessage", "Email is taken");
    }

    @Test
    public void executeShouldBehaveNormally() {
        when(request.getParameter("password")).thenReturn("password1");
        when(request.getParameter("confirmPassword")).thenReturn("password1");

        final String actual = registrationCommand.execute(request, response);
        final String expected = "/game?command=loginForm";
        assertThat(actual, Is.is(expected));
    }


}