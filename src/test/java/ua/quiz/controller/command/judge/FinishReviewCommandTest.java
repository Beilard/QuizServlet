package ua.quiz.controller.command.judge;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FinishReviewCommandTest {
    @InjectMocks
    private FinishReviewCommand finishReviewCommand;
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
    public void executeShouldHaveNormalBehaviour() {
        when(request.getSession()).thenReturn(session);

        final String actual = finishReviewCommand.execute(request, response);
        final String expected = "/game?command=player-PageForm";

        assertThat(actual, Is.is(expected));
        verify(request.getSession(), times(3)).removeAttribute(anyString());
    }
}