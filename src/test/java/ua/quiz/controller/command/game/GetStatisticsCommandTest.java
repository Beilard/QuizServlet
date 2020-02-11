package ua.quiz.controller.command.game;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Status;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetStatisticsCommandTest {
    private static final Long ID = 1L;

    private static final Game GAME = Game.builder()
            .withId(ID)
            .withStatus(Status.REVIEWED)
            .withNumberOfQuestions(10)
            .build();

    @InjectMocks
    private GetStatisticsCommand getStatisticsCommand;
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
    public void executeShouldReturnToPlayerPage() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("joinGameId")).thenReturn("1");
        when(gameService.findById(ID)).thenReturn(Game.builder(GAME)
                .withStatus(Status.PENDING)
                .build());

        final String actual = getStatisticsCommand.execute(request, response);
        final String expected = "/game?command=player-PageForm";

        assertThat(actual, Is.is(expected));
    }

    @Test
    public void executeShouldReturnToStatistics() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("joinGameId")).thenReturn("1");
        when(gameService.findById(ID)).thenReturn(GAME);

        final String actual = getStatisticsCommand.execute(request, response);
        final String expected = "/game?command=player-getStatisticsForm";

        assertThat(actual, Is.is(expected));
        verify(request.getSession(), times(2)).setAttribute(any(), any());
    }
}