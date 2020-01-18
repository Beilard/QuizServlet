package ua.quiz.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class LocaleFilter implements Filter {
    private static final String UTF_8 = "UTF-8";

    private static final String LANGUAGE = "language";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        servletRequest.setCharacterEncoding(UTF_8);

        if (req.getParameter(LANGUAGE) != null) {
            req.getSession().setAttribute(LANGUAGE, req.getParameter(LANGUAGE));
        }

        filterChain.doFilter(req, servletResponse);
    }
}
