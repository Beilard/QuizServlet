package ua.quiz.controller.command.player;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Role;
import ua.quiz.model.dto.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfilePageFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user.getRole() == Role.PLAYER) {
            return "/game?command=playerPageForm";
        } else if (user.getRole() == Role.JUDGE) {
            return "/game?command=judgePageForm";
        }
        return "/game?command=registrationForm";
    }
}
