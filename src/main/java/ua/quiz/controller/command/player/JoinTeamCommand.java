package ua.quiz.controller.command.player;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Team;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.TeamService;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JoinTeamCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(JoinTeamCommand.class);

    private final TeamService teamService;
    private final UserService userService;

    public JoinTeamCommand(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String teamName = request.getParameter("teamNameForJoin");
        User user = (User) request.getSession().getAttribute("user");

        try {
            teamService.joinTeam(user, teamService.findTeamByName(teamName).getId());
            request.getSession().setAttribute("user", userService.findByEmail(user.getEmail()));
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            LOGGER.info("Failed to join");
            request.setAttribute("nameDoesNotExist", true);
            return "/game?command=player-createTeamForm";
        }

        return "/game?command=player-profilePageForm";
    }
}
