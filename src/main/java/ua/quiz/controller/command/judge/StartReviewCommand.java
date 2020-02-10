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
        final String gameIdString = request.getParameter("gameIdToReview");

        Long gameIdToReview;

        try {
            gameIdToReview = Long.parseLong(gameIdString);
        } catch (NumberFormatException e) {
            LOGGER.info("User passed an character in gameId to judge game");
            request.setAttribute("incorrectId", true);
            return "/game?command=judge-viewAllGamesForm";
        }

        if (gameIdToReview <= 0 || gameIdToReview >= Long.MAX_VALUE) {
            LOGGER.info("User passed an incorrect gameId to judge game");
            request.setAttribute("incorrectId", true);
            return "/game?command=judge-viewAllGamesForm";
        }

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
