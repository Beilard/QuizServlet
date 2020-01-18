package ua.quiz.controller.command.player;

import org.apache.log4j.Logger;
import ua.quiz.controller.command.Command;
import ua.quiz.model.dto.User;
import ua.quiz.model.exception.EntityAlreadyExistsException;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.service.TeamService;
import ua.quiz.model.service.UserService;
import ua.quiz.model.service.mapper.TeamMapper;
import ua.quiz.model.service.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateTeamCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CreateTeamCommand.class);

    private final UserService userService;
    private final TeamService teamService;
    private final TeamMapper teamMapper;
    private final UserMapper userMapper;

    public CreateTeamCommand(UserService userService, TeamService teamService, TeamMapper teamMapper, UserMapper userMapper) {
        this.userService = userService;
        this.teamService = teamService;
        this.teamMapper = teamMapper;
        this.userMapper = userMapper;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String teamName = request.getParameter("teamName");
        User user = (User) request.getSession().getAttribute("user");

        try {
            teamService.createTeam(teamName);
        } catch (EntityAlreadyExistsException | IllegalArgumentException e) {
            request.setAttribute("nameTaken", true);
            return "/game?command=player-createTeamForm";
        }
        final Long teamId = teamService.findTeamByName(teamName).getId();

        final User updatedUser = updateUserForSession(user, teamId);
        request.getSession().setAttribute("user", removePasswordFromUser(updatedUser));

        return "/game?command=player-profilePageForm";
    }

    private User updateUserForSession(User user, Long teamId) {
        User userForUpdate = userService.findByEmail(user.getEmail());

        final User updatedUser = makeCaptain(teamId, userForUpdate);

        userService.update(updatedUser);
        return updatedUser;
    }

    private User removePasswordFromUser(User updatedUser) {
        return User.builder(updatedUser)
                .withPassword(null)
                .build();
    }

    private User makeCaptain(Long teamId, User user) {
        return User.builder(user)
                .withCaptain(true)
                .withTeamId(teamId)
                .build();
    }
}
