package ua.quiz.controller.command.game;

import ua.quiz.controller.command.Command;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetStatisticsCommand implements Command {
    private final GameService gameService;

    public GetStatisticsCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Long gameId = Long.parseLong(request.getParameter("gameId"));
        Long correctAnswersCount = gameService.getCorrectAnswersCount(gameService.findById(gameId));

        request.getSession().setAttribute("correctAnswersCount", correctAnswersCount);

        return "/game?command=player-getStatisticsForm";
    }
}
