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
    public boolean joinTeam(User user, Long teamId) {
        if (user == null || teamId == null || teamId <= 0) {
            LOGGER.warn("User or teamId passed to join team are null or illegal");
            throw new IllegalArgumentException("User or teamId passed to join team are null or illegal");
        }
        final UserEntity userEntity = userDao.findById(user.getId()).orElseThrow(EntityNotFoundException::new);
        final UserEntity modifiedUserEntity = updateUserTeam(teamId, userEntity);

        userDao.update(modifiedUserEntity);

        return true;
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


//    @Override
//    public Team findTeamById(Long id) {
//        if (id == null) {
//            LOGGER.warn("id provided for findById was null");
//            throw new IllegalArgumentException("id provided for findById was null");
//        }
//        final TeamEntity teamEntity = teamDao.findById(id).orElseThrow(EntityNotFoundException::new);
//
//        return teamMapper.mapTeamEntityToTeam(teamEntity);
//    }
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
}


