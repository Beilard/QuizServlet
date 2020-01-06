package ua.quiz.model.dao;

import ua.quiz.model.entity.UserEntity;

import java.util.Optional;

public interface UserDao extends CrudDao<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
