package ua.quiz.controller.command.player;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.Team;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.TeamService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JoinTeamCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(JoinTeamCommand.class);

    private final TeamService teamService;

    public JoinTeamCommand(TeamService teamService) {
        this.teamService = teamService;
    }


    //TODO: finish
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String teamName = request.getParameter("teamNameForJoin");
        User user = (User) request.getSession().getAttribute("user");

        try {
            final Team foundTeam = teamService.findTeamByName(teamName);
            teamService.joinTeam(user, foundTeam.getId());
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            LOGGER.info("Failed to join");
        }


        return "/game?command=player-profilePageForm";
    }
}
