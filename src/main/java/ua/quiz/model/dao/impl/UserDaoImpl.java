package ua.quiz.model.dao.impl;

import ua.quiz.model.dao.UserDao;
import ua.quiz.model.domain.User;
import ua.quiz.model.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl extends AbstractCrudDaoImpl<UserEntity> implements UserDao {

    public UserDaoImpl() {
        super(saveQuery, findByIdQuery, findAllQuery, updateQuery, deleteByIdQuery);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    protected UserEntity mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, UserEntity entity) throws SQLException {

    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, UserEntity entity) throws SQLException {

    }
}
