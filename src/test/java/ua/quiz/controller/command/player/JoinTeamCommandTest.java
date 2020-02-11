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
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.TeamService;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JoinTeamCommandTest {
    private static final String TEAM_NAME = "Name";

    @InjectMocks
    private JoinTeamCommand joinTeamCommand;
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
    public void executeShouldReturnToCreateTeamFormDueToEntityNotFound() {
        final Team team = new Team(TEAM_NAME);
        team.setId(1L);

        when(request.getParameter("teamNameForJoin")).thenReturn(TEAM_NAME);
        when(request.getSession()).thenReturn(session);
        when(teamService.findTeamByName(TEAM_NAME)).thenReturn(team);
        doThrow(EntityNotFoundException.class).when(teamService).joinTeam(any(), anyLong());

        final String actual = joinTeamCommand.execute(request, response);
        final String expected = "/game?command=player-createTeamForm";

        assertThat(actual, Is.is(expected));
    }

    @Test
    public void executeShouldHaveNormalBehaviour() {
        final Team team = new Team(TEAM_NAME);
        team.setId(1L);

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(User.builder().withEmail("email@mail.com").build());
        when(request.getParameter("teamNameForJoin")).thenReturn(TEAM_NAME);
        when(teamService.findTeamByName(TEAM_NAME)).thenReturn(team);
        when(userService.findByEmail(anyString())).thenReturn(any());

        final String actual = joinTeamCommand.execute(request, response);
        final String expected = "/game?command=player-profilePageForm";

        assertThat(actual, Is.is(expected));
    }
}