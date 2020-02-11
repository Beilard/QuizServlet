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
import ua.quiz.model.dto.Question;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;

import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PreparePhaseForReviewCommandTest {
    @InjectMocks
    private PreparePhaseForReviewCommand preparePhaseForReviewCommand;
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
        when(request.getSession().getAttribute("gameForReview")).thenReturn(Game.builder()
                .withCurrentPhase(0)
                .withPhases(Collections.singletonList(Phase.builder()
                        .withQuestion(Question
                                .builder()
                                .build())
                        .build()))
                .build());

        final String actual = preparePhaseForReviewCommand.execute(request, response);
        final String expected = "/game?command=judge-reviewForm";

        assertThat(actual, Is.is(expected));
        verify(request.getSession(), times(2)).setAttribute(any(), any());
    }
}