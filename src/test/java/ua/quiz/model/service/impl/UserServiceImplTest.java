package ua.quiz.model.service.impl;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.quiz.model.dao.UserDao;
import ua.quiz.model.dto.Role;
import ua.quiz.model.dto.User;
import ua.quiz.model.entity.RoleEntity;
import ua.quiz.model.entity.UserEntity;
import ua.quiz.model.exception.EmailAlreadyTakenException;
import ua.quiz.model.exception.EntityNotFoundException;
import ua.quiz.model.exception.InvalidCredentialsException;
import ua.quiz.model.service.encoder.PasswordEncoder;
import ua.quiz.model.service.mapper.UserMapper;
import ua.quiz.model.service.validator.UserValidator;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    private static final Long ID = 1L;

    private static final String EMAIL = "email@email.com";

    private static final String PASSWORD = "password";

    private static final String NAME = "Name";

    private static final String SURNAME = "Surname";

    private static final Long TEAM_ID = 2L;

    private static final User USER = User.builder()
            .withId(ID)
            .withName(NAME)
            .withSurname(SURNAME)
            .withEmail(EMAIL)
            .withPassword(PASSWORD)
            .withRole(Role.PLAYER)
            .build();

    private static final UserEntity USER_ENTITY = UserEntity.builder()
            .withId(ID)
            .withName(NAME)
            .withSurname(SURNAME)
            .withEmail(EMAIL)
            .withPassword(PASSWORD)
            .withRoleEntity(RoleEntity.PLAYER)
            .build();

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDao userDao;
    @Mock
    private UserValidator userValidator;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void registerShouldHaveNormalBehaviour() {
        doNothing().when(userValidator).validate(USER);
        when(userDao.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(passwordEncoder.encrypt(PASSWORD)).thenReturn(PASSWORD);

        UserEntity userWithoutId = UserEntity.builder()
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .withName(NAME)
                .withSurname(SURNAME)
                .withRoleEntity(RoleEntity.PLAYER)
                .build();

        userService.register(USER);

        verify(userDao).save(userWithoutId);
    }

    @Test
    public void registerShouldThrowExceptionDueToNullUser() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Null user passed to register");

        userService.register(null);
    }

    @Test
    public void registerShouldThrowExceptionEmailTaken() {
        expectedException.expect(EmailAlreadyTakenException.class);
        expectedException.expectMessage("User with such email already exists");

        when(userDao.findByEmail(EMAIL)).thenReturn(Optional.of(USER_ENTITY));

        userService.register(USER);
    }

    @Test
    public void loginShouldHaveNormalBehaviour() {
        when(userDao.findByEmail(EMAIL)).thenReturn(Optional.of(USER_ENTITY));
        when(passwordEncoder.encrypt(PASSWORD)).thenReturn(PASSWORD);
        when(userMapper.mapUserEntityToUser(USER_ENTITY)).thenReturn(USER);

        User actual = userService.login(EMAIL, PASSWORD);

        assertEquals(USER, actual);
    }

    @Test
    public void loginShouldThrowExceptionDueToNullCredentials() {
        expectedException.expect(InvalidCredentialsException.class);
        expectedException.expectMessage("Incorrect credentials");

        userService.login(null, null);
    }

    @Test
    public void loginShouldThrowExceptionNoEntityFound() {
        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage("User wasn't found with email: " + EMAIL);
        when(userDao.findByEmail(EMAIL)).thenReturn(Optional.empty());

        userService.login(EMAIL, PASSWORD);
    }

    @Test
    public void loginShouldThrowExceptionWrongPassword() {
        expectedException.expect(InvalidCredentialsException.class);
        expectedException.expectMessage("Incorrect credentials");
        when(userDao.findByEmail(EMAIL)).thenReturn(Optional.of(USER_ENTITY));
        when(passwordEncoder.encrypt(PASSWORD)).thenReturn(null);

        userService.login(EMAIL, PASSWORD);
    }

    @Test
    public void updateShouldThrowErrorDueToNullPassed() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("User passed to update is null");

        userService.update(null);
    }

    @Test
    public void updateShouldHaveNormalBehaviour() {
        when(userMapper.mapUserToUserEntity(USER)).thenReturn(USER_ENTITY);

        userService.update(USER);
        verify(userDao).update(USER_ENTITY);
    }

    @Test
    public void findByEmailShouldThrowErrorDueToNullPassed() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Email passed is null");

        userService.findByEmail(null);
    }

    @Test
    public void findByEmailShouldHaveNormalBehaviour() {
        when(userDao.findByEmail(EMAIL)).thenReturn(Optional.of(USER_ENTITY));
        when(userMapper.mapUserEntityToUser(USER_ENTITY)).thenReturn(USER);

        final User actual = userService.findByEmail(EMAIL);

        assertEquals(USER, actual);
    }

    @Test
    public void findByEmailShouldThrowEntityNotFound() {
        expectedException.expect(EntityNotFoundException.class);
        when(userDao.findByEmail(EMAIL)).thenReturn(Optional.empty());

        userService.findByEmail(EMAIL);
    }

    @Test
    public void findByTeamIdShouldThrowErrorDueToNullPassed() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Team ID passed is null");

        userService.findByTeamId(null);
    }


    @Test
    public void findByTeamIdShouldHaveNormalBehaviour() {
        when(userDao.findAllByTeamId(TEAM_ID)).thenReturn(singletonList(USER_ENTITY));
        when(userMapper.mapUserEntityToUser(USER_ENTITY)).thenReturn(USER);

        final List<User> actual = userService.findByTeamId(TEAM_ID);

        assertThat(actual, CoreMatchers.hasItem(USER));
    }
}
