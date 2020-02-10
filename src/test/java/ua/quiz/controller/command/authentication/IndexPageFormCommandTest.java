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
public class IndexPageFormCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private IndexPageFormCommand indexPageFormCommand;

    @Test
    public void executeShouldHaveNormalBehaviour() {
        final String actual = indexPageFormCommand.execute(request, response);
        final String expected = "index.jsp";

        assertThat(actual, Is.is(expected));
    }
}