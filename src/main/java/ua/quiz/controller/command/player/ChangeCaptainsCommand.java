package ua.quiz.controller.command.player;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.TeamService;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeCaptainsCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(ChangeCaptainsCommand.class);

    private final TeamService teamService;
    private final UserService userService;

    public ChangeCaptainsCommand(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        final User user = (User) request.getSession().getAttribute("user");
        final String newCaptainEmail = request.getParameter("newCaptainEmail");

        if (newCaptainEmail.length() <= 0 || newCaptainEmail.length() >= 63) {
            LOGGER.info("User " + user + " tried to change captaincy with an invalid argument");
            return "/game?command=player-profilePageForm";
        }

        try {
            teamService.changeCaptain(userService.findByEmail(newCaptainEmail), user);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            LOGGER.info("User " + user + " tried to change captaincy with an invalid argument");
            return "/game?command=player-profilePageForm";
        }

        final User userWithPassword = userService.findByEmail(user.getEmail());

        request.getSession().setAttribute("user", removePassword(userWithPassword));
        return "/game?command=player-profilePageForm";
    }

    private User removePassword(User userWithPassword) {
        return User.builder(userWithPassword)
                .withPassword(null)
                .build();
    }
}
