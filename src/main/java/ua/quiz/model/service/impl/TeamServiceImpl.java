package ua.quiz.model.service.impl;

import org.apache.log4j.Logger;
import ua.quiz.model.dao.TeamDao;
import ua.quiz.model.dao.UserDao;
import ua.quiz.model.dto.Team;
import ua.quiz.model.dto.User;
import ua.quiz.model.entity.TeamEntity;
import ua.quiz.model.entity.UserEntity;
import ua.quiz.model.exception.EntityAlreadyExistsException;
import ua.quiz.model.service.TeamService;
import ua.quiz.model.service.mapper.TeamMapper;
import ua.quiz.model.service.mapper.UserMapper;

import java.util.Objects;
import java.util.Optional;

public class TeamServiceImpl implements TeamService {
    private static final Logger LOGGER = Logger.getLogger(TeamServiceImpl.class);
    private static final int MAX_TEAM_NAME_LENGTH = 31;
    private static final int MIN_TEAM_NAME_LENGTH = 1;

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
    public void createTeam(String teamName) {
        if (teamName.length() < MIN_TEAM_NAME_LENGTH || teamName.length() > MAX_TEAM_NAME_LENGTH) {
            LOGGER.warn("Provided arguments are incorrect: " + teamName);
            throw new IllegalArgumentException("Provided arguments are incorrect: ");
        }

        Optional<TeamEntity> teamFoundByName = teamDao.findByTeamName(teamName);

        if (teamFoundByName.isPresent()) {
            LOGGER.info("Team with name found " + teamName);
            throw new EntityAlreadyExistsException("Team with name found " + teamName);
        }

        final Team team = new Team(teamName);

        teamDao.save(teamMapper.mapTeamToTeamEntity(team));
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


