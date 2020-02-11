package ua.quiz.controller.command.player;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CheckTeamFormCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private CheckTeamFormCommand checkTeamFormCommand;

    @Test
    public void executeShouldHaveNormalBehaviour() {
        final String actual = checkTeamFormCommand.execute(request, response);
        final String expected = "team-page.jsp";

        assertThat(actual, Is.is(expected));
    }

}