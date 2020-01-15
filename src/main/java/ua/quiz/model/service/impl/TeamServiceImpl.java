package ua.quiz.model.service.impl;

import org.apache.log4j.Logger;
import ua.quiz.model.dao.TeamDao;
import ua.quiz.model.dao.UserDao;
import ua.quiz.model.dto.Team;
import ua.quiz.model.dto.User;
import ua.quiz.model.entity.TeamEntity;
import ua.quiz.model.entity.UserEntity;
import ua.quiz.model.service.TeamService;
import ua.quiz.model.service.mapper.TeamMapper;
import ua.quiz.model.service.mapper.UserMapper;

import java.util.Objects;

public class TeamServiceImpl implements TeamService {
    private static final Logger LOGGER = Logger.getLogger(TeamServiceImpl.class);

    private final UserDao userDao;
    private final TeamDao teamDao;
    private final TeamMapper teamMapper;
    private final UserMapper userMapper;

    public TeamServiceImpl(UserDao userDao, TeamDao teamDao, TeamMapper teamMapper, UserMapper userMapper) {
        this.userDao = userDao;
        this.teamDao = teamDao;
        this.teamMapper = teamMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Team createTeam(User captain, String teamName) {
        if (teamName.length() > 31 || captain == null) {
            LOGGER.warn("Provided arguments are incorrect: " + teamName);
            throw new IllegalArgumentException("Provided arguments are incorrect: ");
        }
        final Team team = new Team(teamName);

        teamDao.save(teamMapper.mapTeamToTeamEntity(team));

        return team;
    }

    @Override
    public void changeCaptain(Long teamId, User newCaptain, User oldCaptain) {
        if (teamId == null || newCaptain == null || oldCaptain == null
                || !Objects.equals(newCaptain.getTeamId(), teamId)
                || newCaptain.getCaptain()) {
            LOGGER.warn("New captain or teamId passed were null");
            throw new IllegalArgumentException("New captain or teamId passed were null");
        }

        swapCaptainStatusBetweenTwoUsers(newCaptain, oldCaptain);

        userDao.update(userMapper.mapUserToUserEntity(oldCaptain));
        userDao.update(userMapper.mapUserToUserEntity(newCaptain));
    }

    @Override
    public Team findTeamByName(String name) {
        if (name == null) {
            LOGGER.warn("String provided for findByName was null");
            throw new IllegalArgumentException("String provided for findByName was null");
        }
        final TeamEntity teamEntity = teamDao.findByTeamName(name).get();

        return teamMapper.mapTeamEntityToTeam(teamEntity);
    }

    private void swapCaptainStatusBetweenTwoUsers(User newCaptain, User oldCaptain) {
        changeCaptainStatus(oldCaptain, false);

        if (oldCaptain.getCaptain()) {
            swapCaptainStatusBetweenTwoUsers(newCaptain, oldCaptain);
            return;
        }

        changeCaptainStatus(newCaptain, true);
    }

    private User changeCaptainStatus(User user, boolean isCaptain) {
        if (user == null) {
            LOGGER.warn("User passed to change captaincy is null");
            throw new IllegalArgumentException("User passed to change captaincy is null");
        }

        return User.builder(user)
                .withCaptain(isCaptain)
                .build();
    }
}


