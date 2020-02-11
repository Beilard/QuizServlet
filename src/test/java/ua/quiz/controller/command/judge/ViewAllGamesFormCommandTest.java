package ua.quiz.controller.command.judge;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ViewAllGamesFormCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private ViewAllGamesFormCommand viewAllGamesFormCommand;

    @Test
    public void executeShouldHaveNormalBehaviour() {
        final String actual = viewAllGamesFormCommand.execute(request, response);
        final String expected = "judge-all-games.jsp";

        assertThat(actual, Is.is(expected));
    }
}