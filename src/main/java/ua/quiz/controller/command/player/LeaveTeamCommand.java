package ua.quiz.controller.command.player;

import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.User;
import ua.quiz.model.service.TeamService;
import ua.quiz.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LeaveTeamCommand implements Command {
    private final TeamService teamService;
    private final UserService userService;

    public LeaveTeamCommand(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user.getCaptain()) {
            request.setAttribute("isCaptainText", true);
            return "/game?command=player-checkTeamForm";
        }
        final User userForUpdate = userService.findByEmail(user.getEmail());
        teamService.leaveTeam(userForUpdate);

        final User updatedUserWithPassword = userService.findByEmail(user.getEmail());
        request.getSession().setAttribute("user", removePassword(updatedUserWithPassword));

        return "/game?command=player-profilePageForm";
    }

    private User removePassword(User userWithPassword) {
        return User.builder(userWithPassword)
                .withPassword(null)
                .build();
    }
}
