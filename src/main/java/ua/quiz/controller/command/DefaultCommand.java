package ua.quiz.controller.command;

import ua.quiz.model.dto.Role;
import ua.quiz.model.dto.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            if (user.getRole() == Role.PLAYER) {
                return "player-page.jsp";
            } else if (user.getRole() == Role.JUDGE) {
                return "judge-page.jsp";
            }
        }
        return "index.jsp";
    }
}
