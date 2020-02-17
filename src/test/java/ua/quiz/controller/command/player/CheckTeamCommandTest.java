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
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckTeamCommandTest {
    private static final User USER = User.builder().withTeamId(1L).build();

    @InjectMocks
    private CheckTeamCommand checkTeamCommand;
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
    public void executeShouldReturnToProfileDueToAbsentTeam() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(USER);
        when(userService.findAllByTeamId(anyLong())).thenThrow(EntityNotFoundException.class);

        final String actual = checkTeamCommand.execute(request, response);
        final String expected = "/game?command=player-PageForm";

        assertThat(actual, Is.is(expected));
    }

    @Test
    public void executeShouldHaveNormalBehaviour() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("user")).thenReturn(USER);
        when(userService.findAllByTeamId(1L)).thenReturn(Collections.singletonList(USER));

        final String actual = checkTeamCommand.execute(request, response);
        final String expected = "/game?command=player-checkTeamForm";

        assertThat(actual, Is.is(expected));
        verify(request.getSession()).setAttribute("usersOfTeam", Collections.singletonList(USER));
    }
}