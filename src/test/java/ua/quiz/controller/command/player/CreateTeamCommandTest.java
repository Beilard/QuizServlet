package ua.quiz.controller.command.player;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.dto.Team;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.EntityAlreadyExistsException;
import ua.quiz.model.service.TeamService;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateTeamCommandTest {
    private static final String TEAM_NAME = "Name";

    private static final User USER = User.builder()
            .withEmail("email@email.com")
            .withCaptain(false)
            .build();

    @InjectMocks
    private CreateTeamCommand createTeamCommand;
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
    public void executeShouldReturnToCreateTeamPageDueToTakenName() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("teamName")).thenReturn(TEAM_NAME);
        doThrow(EntityAlreadyExistsException.class).when(teamService).createTeam(TEAM_NAME);

        final String actual = createTeamCommand.execute(request, response);
        final String expected = "/game?command=player-createTeamForm";

        assertThat(actual, Is.is(expected));
        verify(request).setAttribute(anyString(), any());
    }

    @Test
    public void executeShouldHaveNormalBehaviour() {
        final Team team = new Team(TEAM_NAME);
        team.setId(1L);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("teamName")).thenReturn(TEAM_NAME);
        when(request.getSession().getAttribute("user")).thenReturn(USER);
        when(teamService.findTeamByName(TEAM_NAME)).thenReturn(team);
        when(userService.findByEmail("email@email.com")).thenReturn(USER);

        final String actual = createTeamCommand.execute(request, response);
        final String expected = "/game?command=player-profilePageForm";

        assertThat(actual, Is.is(expected));
        verify(request.getSession()).setAttribute(anyString(), any());
    }
}