package ua.quiz.controller.command.game;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Status;
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
        Long gameId = Long.parseLong(request.getParameter("joinGameId"));
        Game game = gameService.findById(gameId);

        if (game.getStatus() != Status.REVIEWED) {
            return "/game?command=player-PageForm";
        }
        Long correctAnswersCount = gameService.getCorrectAnswersCount(game);

        request.getSession().setAttribute("correctAnswersCount", correctAnswersCount);
        request.getSession().setAttribute("numberOfQuestions", game.getNumberOfQuestions());

        return "/game?command=player-getStatisticsForm";
    }
}
