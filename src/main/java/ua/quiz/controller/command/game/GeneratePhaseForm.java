package ua.quiz.controller.command.game;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Phase;
import ua.quiz.model.dto.Question;
import ua.quiz.model.service.GameService;
import ua.quiz.model.service.PhaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeneratePhaseForm implements Command {
    private static final Logger LOGGER = Logger.getLogger(GeneratePhaseForm.class);

    private final GameService gameService;
    private final PhaseService phaseService;

    public GeneratePhaseForm(GameService gameService, PhaseService phaseService) {
        this.gameService = gameService;
        this.phaseService = phaseService;

    }

    //TODO: check why modifiedGamePhase has null for currentPhase
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final Game game = (Game) request.getSession().getAttribute("game");

        Phase currentPhase = game.getPhases().get(game.getCurrentPhase());
        phaseService.initiatePhase(currentPhase, game.getTimePerQuestion());
        Game modifiedGame = gameService.findById(game.getId());
        request.getSession().setAttribute("game", modifiedGame);
        request.getSession().setAttribute("question", getQuestion(modifiedGame, game));

        return "/game?command=player-viewPhase";
    }

    private Question getQuestion(Game modifiedGame, Game game) {
        return modifiedGame.getPhases().get(game.getCurrentPhase()).getQuestion();
    }
}
