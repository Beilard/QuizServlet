package ua.quiz.controller.command.judge;

import ua.quiz.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JudgePageFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "judge-page.jsp";
    }
}
