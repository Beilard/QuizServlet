package ua.quiz.controller.command.game;

import ua.quiz.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FinishGameCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
