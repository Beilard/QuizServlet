package ua.quiz.model.dao;

import ua.quiz.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudDao<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByTeamId(Long teamId);
}
