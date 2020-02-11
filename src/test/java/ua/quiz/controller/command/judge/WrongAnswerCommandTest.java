package ua.quiz.controller.command.judge;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WrongAnswerCommandTest {
    private static final Game GAME = Game.builder()
            .withNumberOfQuestions(1)
            .withCurrentPhase(0)
            .withPhases(Collections.singletonList(Phase.builder().build()))
            .build();

    @InjectMocks
    private WrongAnswerCommand wrongAnswerCommand;
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
    public void executeShouldReturnFinisReviewCommand() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("gameForReview")).thenReturn(GAME);

        final String actual = wrongAnswerCommand.execute(request, response);
        final String expected = "/game?command=judge-finishReview";

        assertThat(actual, Is.is(expected));
    }

    @Test
    public void executeShouldReturnPreparePhaseCommand() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("gameForReview")).thenReturn(Game.builder(GAME)
                .withNumberOfQuestions(10)
                .build());

        final String actual = wrongAnswerCommand.execute(request, response);
        final String expected = "/game?command=judge-preparePhase";

        assertThat(actual, Is.is(expected));
    }
}