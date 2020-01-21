package ua.quiz.controller.command.game;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.service.GameService;
import ua.quiz.model.service.PhaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FinishPhaseCommand implements Command {
    private final PhaseService phaseService;
    private final GameService gameService;

    public FinishPhaseCommand(PhaseService phaseService, GameService gameService) {
        this.phaseService = phaseService;
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Game game = (Game) request.getSession().getAttribute("game");
        String givenAnswer = request.getParameter("givenAnswer");
        Integer currentPhase = game.getCurrentPhase();

        phaseService.finishPhase(game.getPhases().get(currentPhase), givenAnswer );

        game.setCurrentPhase(currentPhase + 1);
        gameService.updateGame(game);

        request.getSession().setAttribute("game", gameService.findById(game.getId()));

        if (currentPhase >= game.getNumberOfQuestions() - 1) {
            return "/game?command=player-finishGame";
        } else {
            return "/game?command=player-generatePhase";
        }
    }
}
