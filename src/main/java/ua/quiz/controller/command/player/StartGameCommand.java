package ua.quiz.controller.command.player;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.User;
import ua.quiz.model.service.GameService;
import ua.quiz.model.service.mapper.GameMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartGameCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(StartGameCommand.class);

    private final GameService gameService;
    private final GameMapper gameMapper;

    public StartGameCommand(GameService gameService, GameMapper gameMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final Integer numberOfQuestions = Integer.parseInt(request.getParameter("numberOfQuestions"));
        final Integer timePerQuestion = Integer.parseInt(request.getParameter("timePerQuestion"));
        final Long teamId = ((User)(request.getSession().getAttribute("user"))).getTeamId();
        final Game game = gameService.startGame(teamId, numberOfQuestions, timePerQuestion);

        request.getSession().setAttribute("game", game);
        return "/game?command=startGame";
    }
}
