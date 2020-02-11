package ua.quiz.controller.command.game;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.dto.Question;
import ua.quiz.model.service.GameService;
import ua.quiz.model.service.PhaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeneratePhaseFormCommand implements Command {
    private final GameService gameService;
    private final PhaseService phaseService;

    public GeneratePhaseFormCommand(GameService gameService, PhaseService phaseService) {
        this.gameService = gameService;
        this.phaseService = phaseService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final Game game = (Game) request.getSession().getAttribute("game");

        final Phase currentPhase = game.getPhases().get(game.getCurrentPhase());
        phaseService.initiatePhase(currentPhase, game.getTimePerQuestion());
        Game modifiedGame = gameService.findById(game.getId());

        request.getSession().setAttribute("game", modifiedGame);
        request.getSession().setAttribute("question", getQuestion(modifiedGame));
        request.setAttribute("hintUsed", false);

        return "/game?command=player-viewPhase";
    }

    private Question getQuestion(Game modifiedGame) {
        return modifiedGame.getPhases().get(modifiedGame.getCurrentPhase()).getQuestion();
    }
}
