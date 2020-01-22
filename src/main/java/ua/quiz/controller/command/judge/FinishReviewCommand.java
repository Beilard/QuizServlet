package ua.quiz.controller.command.judge;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.User;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FinishReviewCommand implements Command {
    private final GameService gameService;

    public FinishReviewCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final User user = (User) request.getSession().getAttribute("user");
        final Game reviewedGame = (Game) request.getSession().getAttribute("reviewedGame");

        gameService.finishGame(reviewedGame);

        request.getSession().setAttribute("reviewedGame", null);

        return "/game?command=playerPageForm";
    }
}
