package ua.quiz.controller.command.game;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.dto.User;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StartGameCommandTest {
    @InjectMocks
    private StartGameCommand startGameCommand;
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
    public void executeShouldReturnToPlayerPageDueToOutOfBoundsId() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("numberOfQuestions")).thenReturn(String.valueOf(10));
        when(request.getParameter("timePerQuestion")).thenReturn(String.valueOf(60));
        when(request.getSession().getAttribute("user")).thenReturn(User.builder().withTeamId(1L).build());

        final String actual = startGameCommand.execute(request, response);
        final String expected = "/game?command=player-generatePhase";

        assertThat(actual, Is.is(expected));
        verify(gameService).startGame(1L, 10, 60);
        verify(request.getSession()).setAttribute(any(), any());
    }
}