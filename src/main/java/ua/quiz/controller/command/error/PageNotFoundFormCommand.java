package ua.quiz.controller.command.error;

import ua.quiz.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageNotFoundFormCommand implements Command {
        @Override
        public String execute(HttpServletRequest request, HttpServletResponse response) {
            return "404-page.jsp";
        }
}
