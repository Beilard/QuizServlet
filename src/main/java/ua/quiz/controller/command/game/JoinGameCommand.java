package ua.quiz.controller.command.game;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Game;
import ua.quiz.model.dto.Question;
import ua.quiz.model.dto.Status;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.GameService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JoinGameCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(JoinGameCommand.class);

    private final GameService gameService;

    public JoinGameCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final User user = (User) request.getSession().getAttribute("user");
        final String gameIdString = request.getParameter("joinGameId");

        Long gameId;

        try {
            gameId = Long.parseLong(gameIdString);
        } catch (NumberFormatException e) {
            LOGGER.info("User passed a character in gameId to join game");
            request.setAttribute("incorrectId", true);
            return "/game?command=player-PageForm";
        }

        if (gameId <= 0 || gameId >= Long.MAX_VALUE) {
            LOGGER.info("User passed an incorrect gameId to join game");
            request.setAttribute("incorrectId", true);
            return "/game?command=player-PageForm";
        }

        Game foundGame;

        try {
            foundGame = gameService.findById(gameId);
        } catch (EntityNotFoundException e) {
            LOGGER.info("Game with ID " + gameId + "not found");
            request.setAttribute("incorrectId", true);
            return "/game?command=player-PageForm";
        }

        if (!foundGame.getTeamId().equals(user.getTeamId())) {
            LOGGER.info("User tried to join not his team's game. User ID: " + user.getId());
            request.setAttribute("incorrectId", true);
            return "/game?command=playerPageForm";
        }

        if (foundGame.getStatus() == Status.REVIEWED) {
            return "/game?command=player-getStatistics";
        } else if (foundGame.getStatus() == Status.PENDING) {
            return "/game?command=player-PageForm";
        }

        request.getSession().setAttribute("game", foundGame);
        request.getSession().setAttribute("question", getQuestion(foundGame));
        request.setAttribute("hintUsed", getQuestion(foundGame).getHint());

        return "/game?command=player-viewPhase";
    }

    private Question getQuestion(Game modifiedGame) {
        return modifiedGame.getPhases().get(modifiedGame.getCurrentPhase()).getQuestion();
    }
}
