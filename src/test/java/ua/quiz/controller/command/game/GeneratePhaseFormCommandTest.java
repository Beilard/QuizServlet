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
import ua.quiz.model.service.GameService;
import ua.quiz.model.service.PhaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GeneratePhaseFormCommandTest {
    private static final long ID = 1L;

    private static final Game GAME = Game.builder()
            .withId(ID)
            .withCurrentPhase(0)
            .withPhases(Collections.singletonList(Phase.builder()
                    .withQuestion(Question.builder()
                            .build())
                    .build()))
            .withNumberOfQuestions(1)
            .build();

    @InjectMocks
    private GeneratePhaseFormCommand generatePhaseFormCommand;
    @Mock
    private GameService gameService;
    @Mock
    private PhaseService phaseService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    @After
    public void resetMocks() {
        reset(request, response, gameService, phaseService, session);
    }

    @Test
    public void executeShouldHaveNormalBehaviour() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("game")).thenReturn(GAME);
        when(gameService.findById(ID)).thenReturn(GAME);

        final String actual = generatePhaseFormCommand.execute(request, response);
        final String expected = "/game?command=player-viewPhase";

        assertThat(actual, Is.is(expected));
        verify(request.getSession(), times(2)).setAttribute(any(), any());
    }
}