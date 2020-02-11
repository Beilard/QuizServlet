package ua.quiz.controller.command.judge;

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
public class ReviewFormCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private ReviewFormCommand reviewFormCommand;

    @Test
    public void executeShouldHaveNormalBehaviour() {
        final String actual = reviewFormCommand.execute(request, response);
        final String expected = "review-page.jsp";

        assertThat(actual, Is.is(expected));
    }

}