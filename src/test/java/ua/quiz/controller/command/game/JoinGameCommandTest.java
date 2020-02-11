package ua.quiz.controller.command.game;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.dto.Question;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JoinGameCommandTest {
    private static final String GAME_ID = "1";

    private static final Long TEAM_ID = 2L;

    private static final Game GAME = Game.builder()
            .withId(1L)
            .withCurrentPhase(0)
            .withPhases(Collections.singletonList(Phase.builder()
                    .withQuestion(Question.builder()
                            .build())
                    .build()))
            .withNumberOfQuestions(1)
            .withTeamId(TEAM_ID)
            .build();

    private static final User USER = User.builder()
            .withTeamId(TEAM_ID)
            .build();

    @InjectMocks
    private JoinGameCommand joinGameCommand;
    @Mock
    private GameService gameService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @After
    public void resetMocks() {
        reset(request, response, gameService, session);
    }

    @Test
    public void executeShouldReturnToPlayerPageDueToCharactersInGameId() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("joinGameId")).thenReturn("word");

        final String actual = joinGameCommand.execute(request, response);
        final String expected = "/game?command=player-PageForm";

        assertThat(actual, Is.is(expected));
        verify(request).setAttribute(any(), any());
    }

    @Test
    public void executeShouldReturnToPlayerPageDueToOutOfBoundsId() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("joinGameId")).thenReturn("-1");

        final String actual = joinGameCommand.execute(request, response);
        final String expected = "/game?command=player-PageForm";

        assertThat(actual, Is.is(expected));
        verify(request).setAttribute(any(), any());
    }

    @Test
    public void executeShouldReturnToPlayerPageDueEntityNotFound() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("joinGameId")).thenReturn(GAME_ID);
        when(gameService.findById(any())).thenThrow(EntityNotFoundException.class);

        final String actual = joinGameCommand.execute(request, response);
        final String expected = "/game?command=player-PageForm";

        assertThat(actual, Is.is(expected));
        verify(request).setAttribute(any(), any());
    }

    @Test
    public void executeShouldHaveNormalBehaviour() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("joinGameId")).thenReturn(GAME_ID);
        when(request.getSession().getAttribute("user")).thenReturn(USER);
        when(gameService.findById(1L)).thenReturn(GAME);


        final String actual = joinGameCommand.execute(request, response);
        final String expected = "/game?command=player-viewPhase";

        assertThat(actual, Is.is(expected));
        verify(request.getSession(), times(2)).setAttribute(any(), any());
        verify(request).setAttribute(any(), any());
    }
}