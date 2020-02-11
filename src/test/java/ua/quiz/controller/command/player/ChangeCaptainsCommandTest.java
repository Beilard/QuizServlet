package ua.quiz.controller.command.player;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.TeamService;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChangeCaptainsCommandTest {
    private static final String EMAIL = "email@email.com";

    private static final User USER = User.builder()
            .withEmail(EMAIL)
            .build();

    @InjectMocks
    private ChangeCaptainsCommand changeCaptainsCommand;
    @Mock
    private UserService userService;
    @Mock
    private TeamService teamService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @After
    public void resetMocks() {
        reset(request, response, userService, teamService, session);
    }

    @Test
    public void executeShouldReturnToProfileDueToIncorrectEmailLength() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("newCaptainEmail")).thenReturn("");

        final String actual = changeCaptainsCommand.execute(request, response);
        final String expected = "/game?command=player-profilePageForm";

        assertThat(actual, Is.is(expected));
    }

    @Test
    public void executeShouldReturnToProfileDueToEntityNotFound() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("newCaptainEmail")).thenReturn(EMAIL);
        doThrow(EntityNotFoundException.class).when(userService).findByEmail(anyString());

        final String actual = changeCaptainsCommand.execute(request, response);
        final String expected = "/game?command=player-profilePageForm";

        assertThat(actual, Is.is(expected));
    }

    @Test
    public void executeShouldHaveNormalBehaviour() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("newCaptainEmail")).thenReturn(EMAIL);
        when(userService.findByEmail(EMAIL)).thenReturn(USER);
        when(request.getSession().getAttribute("user")).thenReturn(USER);

        final String actual = changeCaptainsCommand.execute(request, response);
        final String expected = "/game?command=player-profilePageForm";

        assertThat(actual, Is.is(expected));
        verify(request.getSession()).setAttribute("user", USER);
    }

}