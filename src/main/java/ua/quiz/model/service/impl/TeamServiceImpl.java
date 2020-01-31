package ua.quiz.model.service.impl;

import org.apache.log4j.Logger;
import ua.quiz.model.dao.TeamDao;
import ua.quiz.model.dao.UserDao;
import ua.quiz.model.dto.Team;
import ua.quiz.model.dto.User;
import ua.quiz.model.entity.TeamEntity;
import ua.quiz.model.entity.UserEntity;
import ua.quiz.model.exception.EntityAlreadyExistsException;
import ua.quiz.model.exception.EntityNotFoundException;
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
        final TeamEntity team = new TeamEntity(teamName);
        teamDao.save(team);
    }

    @Override
    public void changeCaptain(User newCaptain, User oldCaptain) {
        if (newCaptain == null || oldCaptain == null
                || !Objects.equals(newCaptain.getTeamId(), oldCaptain.getTeamId())
                || newCaptain.getCaptain()) {
            LOGGER.warn("New captain or teamId passed were null");
            throw new IllegalArgumentException("New captain or teamId passed were null");
        }

        User oldCaptainWithEmail = getOldCaptainWithEmail(oldCaptain);
        userDao.update(userMapper.mapUserToUserEntity(changeCaptainStatus(oldCaptainWithEmail, false)));
        userDao.update(userMapper.mapUserToUserEntity(changeCaptainStatus(newCaptain, true)));
    }

    @Override
    public void joinTeam(User user, Long teamId) {
        if (user == null || teamId == null || teamId <= 0) {
            LOGGER.warn("User or teamId passed to join team are null or illegal");
            throw new IllegalArgumentException("User or teamId passed to join team are null or illegal");
        }
        final UserEntity userEntity = userDao.findById(user.getId()).orElseThrow(EntityNotFoundException::new);
        final UserEntity modifiedUserEntity = updateUserTeam(teamId, userEntity);

        userDao.update(modifiedUserEntity);
    }

    @Override
    public void leaveTeam(User user) {
        if (user == null || user.getCaptain()) {
            LOGGER.warn("User passed to leave is null or captain");
            throw new IllegalArgumentException("User passed to leave is null or captain");
        }

        userDao.update(userMapper.mapUserToUserEntity(removeTeam(user)));
    }

    @Override
    public Team findTeamByName(String name) {
        if (name == null) {
            LOGGER.warn("String provided for findByName was null");
            throw new IllegalArgumentException("String provided for findByName was null");
        }
        final TeamEntity teamEntity = teamDao.findByTeamName(name).orElseThrow(EntityNotFoundException::new);

        return teamMapper.mapTeamEntityToTeam(teamEntity);
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

    private UserEntity updateUserTeam(Long teamId, UserEntity userEntity) {
        return UserEntity.builder()
                .withId(userEntity.getId())
                .withEmail(userEntity.getEmail())
                .withPassword(userEntity.getPassword())
                .withCaptain(userEntity.getCaptain())
                .withName(userEntity.getName())
                .withSurname(userEntity.getSurname())
                .withTeamId(teamId)
                .withRoleEntity(userEntity.getRoleEntity())
                .build();
    }

    private User removeTeam(User user) {
        return User.builder(user)
                .withTeamId(null)
                .build();
    }

    private User getOldCaptainWithEmail(User oldCaptain) {
        return userMapper.mapUserEntityToUser(userDao.findByEmail(oldCaptain.getEmail())
                .orElseThrow(EntityNotFoundException::new));
    }
}


