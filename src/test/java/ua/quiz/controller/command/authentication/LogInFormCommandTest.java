package ua.quiz.controller.command.authentication;

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
public class LogInFormCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private LogInFormCommand logInFormCommand;

    @Test
    public void executeShouldHaveNormalBehaviour() {
        final String actual = logInFormCommand.execute(request, response);
        final String expected = "login.jsp";

        assertThat(actual, Is.is(expected));
    }
}