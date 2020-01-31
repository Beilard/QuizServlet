package ua.quiz.controller.command.judge;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.service.GameService;
import ua.quiz.model.service.PhaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RightAnswerCommand implements Command {
    private final GameService gameService;
    private final PhaseService phaseService;

    public RightAnswerCommand(GameService gameService, PhaseService phaseService) {
        this.gameService = gameService;
        this.phaseService = phaseService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Game game = (Game) request.getSession().getAttribute("gameForReview");
        final Integer currentPhase = game.getCurrentPhase();

        phaseService.reviewPhasePositively(game.getPhases().get(currentPhase));

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
