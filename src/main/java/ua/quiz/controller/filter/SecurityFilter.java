package ua.quiz.controller.filter;

import ua.quiz.model.dto.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

public class SecurityFilter implements Filter {
    private static final int FIRST_ELEMENT_OF_ARRAY = 0;

    private static final String JUDGE_ROLE_NAME = "judge";

    private static final String PLAYER_ROLE_NAME = "player";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String command = request.getParameter("command");
        final User user = (User) request.getSession().getAttribute("user");

        String accessModifier = urlSplitter(command);

        if (!accessModifier.equals(PLAYER_ROLE_NAME) && !accessModifier.equals(JUDGE_ROLE_NAME)) {
            filterChain.doFilter(request, servletResponse);
            return;
        }

        if (accessModifier.equals(user.getRole().name().toLowerCase())
                || JUDGE_ROLE_NAME.equals(user.getRole().name().toLowerCase())) {
            filterChain.doFilter(request, servletResponse);
        } else {
            request.getRequestDispatcher("access-error.jsp").forward(request, servletResponse);
        }
    }

    private String urlSplitter(String command) {
        if (Objects.isNull(command)) {
            command = "";
        }
        String[] strings = command.split("-");
        return strings[FIRST_ELEMENT_OF_ARRAY];
    }
}
