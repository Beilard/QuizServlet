package ua.quiz.controller.command.judge;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StartReviewCommandTest {
    private static final String GAME_ID = "1";

    @InjectMocks
    private StartReviewCommand startReviewCommand;
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
    public void executeShouldReturnToViewGamesDueToCharactersInGameId() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("gameIdToReview")).thenReturn("abcd");

        final String actual = startReviewCommand.execute(request, response);
        final String expected = "/game?command=judge-viewAllGamesForm";

        assertThat(actual, Is.is(expected));
        verify(request).setAttribute("incorrectId", true);
    }

    @Test
    public void executeShouldReturnToViewGamesDueToGameIdOutOfBounds() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("gameIdToReview")).thenReturn("-1");

        final String actual = startReviewCommand.execute(request, response);
        final String expected = "/game?command=judge-viewAllGamesForm";

        assertThat(actual, Is.is(expected));
        verify(request).setAttribute("incorrectId", true);
    }

    @Test
    public void executeShouldReturnToViewGamesDueToEntityNotFound() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("gameIdToReview")).thenReturn(GAME_ID);
        when(gameService.findById(1L)).thenThrow(EntityNotFoundException.class);

        final String actual = startReviewCommand.execute(request, response);
        final String expected = "/game?command=judge-viewAllGamesForm";

        assertThat(actual, Is.is(expected));
    }

    @Test
    public void executeShouldHaveNormalBehaviour() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("gameIdToReview")).thenReturn(GAME_ID);

        final String actual = startReviewCommand.execute(request, response);
        final String expected = "/game?command=judge-preparePhase";

        assertThat(actual, Is.is(expected));
        verify(request.getSession()).setAttribute(any(), any());
    }





}