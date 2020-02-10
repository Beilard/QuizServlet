package ua.quiz.controller.command.authentication;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogInCommandTest {
    private static final String EMAIL = "email@mail.com";

    private static final String PASSWORD = "password123#";


    @InjectMocks
    private LogInCommand logInCommand;
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
    public void executeShouldReturnBackToLoginFormDueToNullCredentials() {
        when(request.getParameter("email")).thenReturn(null);
        final String expected = "/game?command=loginForm";

        final String actual = logInCommand.execute(request, response);

        assertThat(actual, is(expected));
    }

    @Test
    public void executeShouldReturnBackToLoginFormDueToAbsentRegistration() {
        when(request.getParameter("email")).thenReturn(EMAIL);
        when(request.getParameter("password")).thenReturn(PASSWORD);
        when(userService.login(EMAIL, PASSWORD)).thenThrow(EntityNotFoundException.class);
        when(userService.findByEmail(EMAIL)).thenReturn(User.builder()
                .withEmail(EMAIL)
                .build());
        final String expected = "/game?command=loginForm";

        final String actual = logInCommand.execute(request, response);

        assertThat(actual, is(expected));
    }

    @Test
    public void executeShouldHaveNormalBehaviour() {
        when(request.getParameter("email")).thenReturn(EMAIL);
        when(request.getParameter("password")).thenReturn(PASSWORD);
        when(request.getSession()).thenReturn(session);
        when(userService.login(EMAIL, PASSWORD)).thenReturn(User.builder()
                .withPassword(PASSWORD)
                .withEmail(EMAIL)
                .build());

        final String expected = "/game?command=indexPageForm";

        final String actual = logInCommand.execute(request, response);

        assertThat(actual, is(expected));
    }

}