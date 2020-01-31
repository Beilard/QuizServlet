package ua.quiz.controller.command.judge;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WrongAnswerCommand implements Command {
    private final GameService gameService;

    public WrongAnswerCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Game game = (Game) request.getSession().getAttribute("gameForReview");
        final Integer currentPhase = game.getCurrentPhase();

        game.setCurrentPhase(currentPhase + 1);
        gameService.updateGame(game);

        request.getSession().setAttribute("gameForReview", gameService.findById(game.getId()));
        if (currentPhase >= game.getNumberOfQuestions() - 1) {
            return "/game?command=judge-finishReview";
        } else {
            return "/game?command=judge-preparePhase";
        }
    }
}
