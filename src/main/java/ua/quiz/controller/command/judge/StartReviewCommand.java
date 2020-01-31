package ua.quiz.controller.command.judge;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartReviewCommand implements Command {
    private static Logger LOGGER = Logger.getLogger(StartReviewCommand.class);
    private final GameService gameService;

    public StartReviewCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final Long gameIdToReview = Long.parseLong(request.getParameter("gameIdToReview"));
        Game gamePreparedForReview;

        try {
            gamePreparedForReview= gameService.startReview(gameService.findById(gameIdToReview));
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            LOGGER.info("Game ID passed wasn't found");
            return "/game?command=judge-viewAllGamesForm";
        }
        request.getSession().setAttribute("gameForReview", gamePreparedForReview);

        return "/game?command=judge-preparePhase";
    }
}
