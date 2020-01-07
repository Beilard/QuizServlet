package ua.quiz.model.service.mapper;

import org.junit.Test;
import ua.quiz.model.domain.Role;
import ua.quiz.model.domain.User;
import ua.quiz.model.entity.RoleEntity;
import ua.quiz.model.entity.UserEntity;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserMapperTest {
    private static final Long ID = 0L;

    private static final String NAME = "Ivan";

    private static final String SURNAME = "Popov";

    private static final String EMAIL = "Ivan@mail.com";

    private static final String PASSWORD = "Qwerty123#";

    private static final UserMapper USER_MAPPER = new UserMapper();

    @Test
    public void mapEntityToUserShouldReturnUser() {
        final UserEntity userEntity = UserEntity.builder()
                .withId(ID)
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .withName(NAME)
                .withSurname(SURNAME)
                .withRole(RoleEntity.PLAYER)
                .build();

        final User user = USER_MAPPER.mapUserEntityToUser(userEntity);
        assertThat("mapping id has failed", user.getId(), is(ID));
        assertThat("mapping name has failed", user.getName(), is(NAME));
        assertThat("mapping surname has failed", user.getSurname(), is(SURNAME));
        assertThat("mapping email has failed", user.getEmail(), is(EMAIL));
        assertThat("mapping password has failed", user.getPassword(), is(PASSWORD));
        assertThat("mapping roles has failed", user.getRole(), is(Role.PLAYER));
    }

    @Test
    public void mapUserToUserEntityShouldReturnUserEntity() {
        final User user = User.builder()
                .withId(ID)
                .withEmail(EMAIL)
                .withPassword(PASSWORD)
                .withName(NAME)
                .withSurname(SURNAME)
                .withRole(Role.PLAYER)
                .build();
        final UserEntity userEntity = USER_MAPPER.mapUserToUserEntity(user);
        assertThat("mapping id has failed", userEntity.getId(), is(ID));
        assertThat("mapping name has failed", userEntity.getName(), is(NAME));
        assertThat("mapping surname has failed", userEntity.getSurname(), is(SURNAME));
        assertThat("mapping email has failed", userEntity.getEmail(), is(EMAIL));
        assertThat("mapping password has failed", userEntity.getPassword(), is(PASSWORD));
        assertThat("mapping roles has failed", userEntity.getRoleEntity(), is(Role.PLAYER));
    }
}