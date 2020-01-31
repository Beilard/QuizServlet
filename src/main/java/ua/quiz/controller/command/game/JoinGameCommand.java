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
        final Long gameId = Long.parseLong(request.getParameter("joinGameId"));
        Game foundGame;

        try {
            foundGame = gameService.findById(gameId);
        } catch (EntityNotFoundException e) {
            LOGGER.info("Game with ID " + gameId + "not found");
            return "/game?command=player-PageForm";
        }

        if (foundGame.getStatus() == Status.REVIEWED) {
            return "/game?command=player-getStatistics";
        } else if (foundGame.getStatus() == Status.PENDING) {
            return "/game?command=player-PageForm";
        }

        if (!foundGame.getTeamId().equals(user.getTeamId())) {
            LOGGER.info("User tried to join not his team's game. User ID: " + user.getId());
            return "/game?command=playerPageForm";
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
