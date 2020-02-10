package ua.quiz.model.service.impl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.dao.TeamDao;
import ua.quiz.model.dao.UserDao;
import ua.quiz.model.dto.Team;
import ua.quiz.model.dto.User;
import ua.quiz.model.entity.TeamEntity;
import ua.quiz.model.entity.UserEntity;
import ua.quiz.model.exception.EntityAlreadyExistsException;
import ua.quiz.model.service.mapper.TeamMapper;
import ua.quiz.model.service.mapper.UserMapper;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TeamServiceImplTest {
    private static final Long TEAM_ID = 1L;

    private static final Long OLD_CAPTAIN_ID = 2L;

    private static final Long NEW_CAPTAIN_ID = 3L;

    private static final String OLD_CAPTAIN_EMAIL = "Old@mail.com";

    private static final String NEW_CAPTAIN_EMAIL = "New@mail.com";

    private static final User OLD_CAPTAIN = User.builder()
            .withEmail(OLD_CAPTAIN_EMAIL)
            .withId(OLD_CAPTAIN_ID)
            .withTeamId(TEAM_ID)
            .withCaptain(true)
            .build();

    private static final UserEntity OLD_CAPTAIN_ENTITY = UserEntity.builder()
            .withEmail(OLD_CAPTAIN_EMAIL)
            .withId(OLD_CAPTAIN_ID)
            .withTeamId(TEAM_ID)
            .withCaptain(true)
            .build();

    private static final User NEW_CAPTAIN = User.builder()
            .withEmail(NEW_CAPTAIN_EMAIL)
            .withId(NEW_CAPTAIN_ID)
            .withTeamId(TEAM_ID)
            .withCaptain(false)
            .build();

    private static final UserEntity NEW_CAPTAIN_ENTITY = UserEntity.builder()
            .withEmail(NEW_CAPTAIN_EMAIL)
            .withId(NEW_CAPTAIN_ID)
            .withCaptain(false)
            .build();

    private static final String TEAM_NAME = "Name";

    private static final Team TEAM = new Team(TEAM_NAME);

    private static final TeamEntity TEAM_ENTITY = new TeamEntity(TEAM_NAME);

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private TeamServiceImpl teamService;
    @Mock
    private UserDao userDao;
    @Mock
    private UserMapper userMapper;
    @Mock
    private TeamDao teamDao;
    @Mock
    private TeamMapper teamMapper;


    @Test
    public void createTeamShouldThrowExceptionInvalidNameLength() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Team name length is out of bounds");

        teamService.createTeam("");
    }

    @Test
    public void createTeamShouldThrowExceptionEntityExists() {
        expectedException.expect(EntityAlreadyExistsException.class);
        expectedException.expectMessage("User tried to create a team with reserved name: " + TEAM_NAME);
        when(teamDao.findByTeamName(TEAM_NAME)).thenReturn(Optional.of(TEAM_ENTITY));

        teamService.createTeam(TEAM_NAME);
    }

    @Test
    public void createTeamShouldHaveNormalBehaviour() {
        when(teamDao.findByTeamName(TEAM_NAME)).thenReturn(Optional.empty());

        teamService.createTeam(TEAM_NAME);

        verify(teamDao).save(TEAM_ENTITY);
    }

    @Test
    public void changeCaptainShouldThrowIllegalArgumentExceptionDueToNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("New captain or old captain passed were null or illegal");

        teamService.changeCaptain(null, null);
    }


    @Test
    public void changeCaptainShouldHaveNormalBehaviour() {
        when(userDao.findByEmail(OLD_CAPTAIN_EMAIL)).thenReturn(Optional.of(OLD_CAPTAIN_ENTITY));
        when(userMapper.mapUserEntityToUser(OLD_CAPTAIN_ENTITY)).thenReturn(OLD_CAPTAIN);
        when(userMapper.mapUserToUserEntity(any())).thenReturn(OLD_CAPTAIN_ENTITY);

        teamService.changeCaptain(NEW_CAPTAIN, OLD_CAPTAIN);
        verify(userDao, times(2)).update(OLD_CAPTAIN_ENTITY);
    }

    @Test
    public void joinTeamShouldThrowExceptionDueToNullPassed() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("User or teamId passed to join team are null or illegal");

        teamService.joinTeam(null, null);
    }

    @Test
    public void joinTeamShouldHaveNormalBehaviour() {
        when(userDao.findById(OLD_CAPTAIN_ID)).thenReturn(Optional.of(OLD_CAPTAIN_ENTITY));

        teamService.joinTeam(OLD_CAPTAIN, TEAM_ID);

        verify(userDao).update(OLD_CAPTAIN_ENTITY);
    }

    @Test
    public void leaveTeamShouldThrowExceptionDueToNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("User passed to leave is null or captain");

        teamService.leaveTeam(null);
    }

    @Test
    public void leaveTeamShouldThrowExceptionDueToCaptainStatus() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("User passed to leave is null or captain");

        teamService.leaveTeam(OLD_CAPTAIN);
    }

    @Test
    public void leaveTeamShouldHaveNormalBehaviour() {
        when(userMapper.mapUserToUserEntity(any())).thenReturn(NEW_CAPTAIN_ENTITY);

        teamService.leaveTeam(NEW_CAPTAIN);

        verify(userDao).update(NEW_CAPTAIN_ENTITY);
    }

    @Test
    public void findTeamByNameShouldThrowExceptionDueToNull() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("String provided for findTeamByName was null");

        teamService.findTeamByName(null);
    }

    @Test
    public void findTeamByNameShouldHaveNormalBehaviour() {
        when(teamDao.findByTeamName(TEAM_NAME)).thenReturn(Optional.of(TEAM_ENTITY));
        when(teamMapper.mapTeamEntityToTeam(TEAM_ENTITY)).thenReturn(TEAM);

        Team actual = teamService.findTeamByName(TEAM_NAME);

        Assert.assertEquals(TEAM, actual);
    }
}
