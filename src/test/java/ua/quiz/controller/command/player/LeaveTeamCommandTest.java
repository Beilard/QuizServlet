package ua.quiz.controller.command.player;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.dto.User;
import ua.quiz.model.service.TeamService;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LeaveTeamCommandTest {
    private static final String EMAIL = "email@mail.com";

    private static final User USER = User.builder()
            .withEmail(EMAIL)
            .withCaptain(false)
            .build();

    @InjectMocks
    private LeaveTeamCommand leaveTeamCommand;
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
    public void executeShouldReturnToTeamPageDueToCaptainStatus() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(User.builder(USER)
                .withCaptain(true)
                .build());

        final String actual = leaveTeamCommand.execute(request, response);
        final String expected = "/game?command=player-checkTeamForm";

        assertThat(actual, Is.is(expected));
        verify(request).setAttribute(anyString(), any());
    }

    @Test
    public void executeShouldHaveNormalBehaviour() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(USER);
        when(userService.findByEmail(EMAIL)).thenReturn(USER);

        final String actual = leaveTeamCommand.execute(request, response);
        final String expected = "/game?command=player-profilePageForm";

        assertThat(actual, Is.is(expected));
    }

}