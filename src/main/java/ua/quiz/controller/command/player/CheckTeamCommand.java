package ua.quiz.controller.command.player;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CheckTeamCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CheckTeamCommand.class);

    private final UserService userService;

    public CheckTeamCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final User user = (User) request.getSession().getAttribute("user");

        try {
            List<User> usersOfTeam = userService.findAllByTeamId(user.getTeamId());
            request.getSession().setAttribute("usersOfTeam", usersOfTeam);
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            LOGGER.info("Failed to create user list for team page");
            return "/game?command=player-PageForm";
        }

        return "/game?command=player-checkTeamForm";
    }
}
