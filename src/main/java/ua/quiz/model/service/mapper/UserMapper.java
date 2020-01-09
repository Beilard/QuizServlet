package ua.quiz.model.service.mapper;

import ua.quiz.model.domain.Role;
import ua.quiz.model.domain.User;
import ua.quiz.model.entity.RoleEntity;
import ua.quiz.model.entity.UserEntity;

public class UserMapper {
    public User mapUserEntityToUser(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return User.builder()
                .withId(userEntity.getId())
                .withEmail(userEntity.getEmail())
                .withPassword(userEntity.getPassword())
                .withName(userEntity.getName())
                .withSurname(userEntity.getSurname())
                .withTeamId(userEntity.getTeamId())
                .withRole(Role.valueOf(userEntity.getRoleEntity().name()))
                .build();
    }

    public UserEntity mapUserToUserEntity(User user) {
        return UserEntity.builder()
                .withId(user.getId())
                .withEmail(user.getEmail())
                .withPassword(user.getPassword())
                .withName(user.getName())
                .withSurname(user.getSurname())
                .withTeamId(user.getTeamId())
                .withRole(RoleEntity.valueOf(user.getRole().name()))
                .build();
    }
}
