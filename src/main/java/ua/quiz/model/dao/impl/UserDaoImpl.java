package ua.quiz.model.dao.impl;

import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.dao.UserDao;
import ua.quiz.model.entity.RoleEntity;
import ua.quiz.model.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl extends AbstractCrudDaoImpl<UserEntity> implements UserDao {
    private static final String SAVE_QUERY = "INSERT INTO users(email, password, name, surname, team_id) VALUES (?,?,?,?,?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users INNER JOIN roles ON role_id = roles.id WHERE users.id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM user INNER JOIN role ON role_id = role.id ORDER BY user.id DESC LIMIT ?, ?";
    private static final String UPDATE_QUERY = "UPDATE users SET email = ?, password = ?, name = ?, surname = ?, team_id =? WHERE id = ?";

    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM users INNER JOIN role ON role_id = role.id WHERE email = ?";


    public UserDaoImpl(DBConnector dbConnector) {
        super(dbConnector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return findByStringParam(email, FIND_BY_EMAIL_QUERY);
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, UserEntity entity) throws SQLException {
        preparedStatement.setString(1, entity.getEmail());
        preparedStatement.setString(2,  entity.getPassword());
        preparedStatement.setString(3,  entity.getName());
        preparedStatement.setString(4,  entity.getSurname());
        preparedStatement.setLong(5,  entity.getTeamId());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, UserEntity entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setLong(6, entity.getId());
    }

    @Override
    protected Optional<UserEntity> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Optional.ofNullable(UserEntity.builder()
        .withId(resultSet.getLong("id"))
        .withEmail(resultSet.getString("email"))
        .withPassword(resultSet.getString("password"))
        .withName(resultSet.getString("name"))
        .withSurname(resultSet.getString("surname"))
        .withTeamId(resultSet.getLong("team_id"))
        .withRole(RoleEntity.valueOf(resultSet.getString("role_name").toUpperCase()))
        .build());
    }
}