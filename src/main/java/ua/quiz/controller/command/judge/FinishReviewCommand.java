package ua.quiz.controller.command.judge;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
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
        final Game reviewedGame = (Game) request.getSession().getAttribute("gameForReview");

        gameService.finishReview(reviewedGame);

        request.getSession().removeAttribute("reviewedPhase");
        request.getSession().removeAttribute("reviewedQuestion");
        request.getSession().removeAttribute("gameForReview");

        return "/game?command=player-PageForm";
    }
}
