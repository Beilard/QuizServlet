package ua.quiz.controller.command.game;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FinishGameCommand implements Command {
    private final GameService gameService;

    public FinishGameCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Game game = (Game) request.getSession().getAttribute("game");
        gameService.finishGame(game);
        request.getSession().removeAttribute("game");
        request.getSession().removeAttribute("question");
        return "/game?command=player-PageForm";
    }
}
