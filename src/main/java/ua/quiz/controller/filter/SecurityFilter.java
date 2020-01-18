package ua.quiz.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.FORWARD
})
public class SecurityFilter implements Filter {
    private static final int FIRST_ELEMENT_OF_ARRAY = 0;

    private static final String JUDGE_ROLE_NAME = "judge";

    private static final String PLAYER_ROLE_NAME = "player";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String command = request.getParameter("command");
        String role = (String) request.getSession().getAttribute("role");
        String accessModifier = urlSplitter(command);

        if (!accessModifier.equals(PLAYER_ROLE_NAME) && !accessModifier.equals(JUDGE_ROLE_NAME)){
            filterChain.doFilter(request, servletResponse);
            return;
        }

        if (accessModifier.equals(role) || JUDGE_ROLE_NAME.equals(role)) {
            filterChain.doFilter(request, servletResponse);
        } else {
            request.getRequestDispatcher("access-error.jsp").forward(request, servletResponse);
        }
    }

    //TODO: something or empty;
    private String urlSplitter(String command) {
        if (command == null) {
            return "/game?command=pageNotFoundForm";
        }
        String[] strings = command.split("-");
        return strings[FIRST_ELEMENT_OF_ARRAY];
    }
}
