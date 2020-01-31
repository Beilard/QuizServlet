package ua.quiz.controller.command.game;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.service.GameService;
import ua.quiz.model.service.PhaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProvideHintCommand implements Command {
    private final PhaseService phaseService;
    private final GameService gameService;

    public ProvideHintCommand(PhaseService phaseService, GameService gameService) {
        this.phaseService = phaseService;
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final Game game = (Game) request.getSession().getAttribute("game");

        phaseService.useHint(game.getPhases().get(game.getCurrentPhase()));

        request.getSession().setAttribute("game", gameService.findById(game.getId()));
        request.setAttribute("hintUsed", true);

        return "/game?command=player-viewPhase";
    }
}
