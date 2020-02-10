package ua.quiz.controller.command.authentication;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class LogOutCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @InjectMocks
    private LogOutCommand logOutCommand;

    @Test
    public void execute() {
        Mockito.when(request.getSession()).thenReturn(session);

        final String actual = logOutCommand.execute(request, response);
        final String expected = "/";

        Mockito.verify(session).invalidate();
        assertThat(actual, Is.is(expected));
    }
}