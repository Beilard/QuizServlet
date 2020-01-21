package ua.quiz.model.dao.impl;

import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.dao.UserDao;
import ua.quiz.model.entity.RoleEntity;
import ua.quiz.model.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractCrudDaoImpl<UserEntity> implements UserDao {
    private static final String SAVE_QUERY =
            "INSERT INTO user(email, password, name, surname) VALUES (?,?,?,?)";

    private static final String FIND_BY_ID_QUERY =
            "SELECT * FROM user INNER JOIN role ON user.role_id = role.role_id WHERE user.user_id = ?";

    private static final String FIND_ALL_QUERY =
            "SELECT * FROM user INNER JOIN role ON user.role_id = role.role_id ORDER BY user.user_id DESC LIMIT ?, ?";

    private static final String UPDATE_QUERY =
            "UPDATE user SET email = ?, password = ?, name = ?, surname = ?, is_captain = ?, team_id = ? WHERE user_id = ?";

    private static final String COUNT_QUERY =
            "SELECT COUNT(*) AS count FROM user";

    private static final String FIND_BY_EMAIL_QUERY =
            "SELECT * FROM user INNER JOIN role ON user.role_id = role.role_id WHERE email = ?";

    private static final String FIND_ALL_BY_TEAM_ID =
            "SELECT * FROM user INNER JOIN role ON user.role_id = role.role_id WHERE team_id = ? GROUP BY user.user_id";

    public UserDaoImpl(DBConnector dbConnector) {
        super(dbConnector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, COUNT_QUERY);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return findByStringParam(email, FIND_BY_EMAIL_QUERY);
    }

    @Override
    public List<UserEntity> findAllByTeamId(Long teamId) {
        return findListByLongParam(teamId, FIND_ALL_BY_TEAM_ID);
    }

    @Override
    protected void mapForInsertStatement(PreparedStatement preparedStatement, UserEntity entity) throws SQLException {
        preparedStatement.setString(1, entity.getEmail());
        preparedStatement.setString(2, entity.getPassword());
        preparedStatement.setString(3, entity.getName());
        preparedStatement.setString(4, entity.getSurname());
    }

    @Override
    protected void mapForUpdateStatement(PreparedStatement preparedStatement, UserEntity entity) throws SQLException {
        mapForInsertStatement(preparedStatement, entity);
        preparedStatement.setBoolean(5, entity.getCaptain());
        mapPreparedStatementForTeamId(preparedStatement, entity);
        preparedStatement.setLong(7, entity.getId());
    }

    @Override
    protected Optional<UserEntity> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Optional.ofNullable(UserEntity.builder()
                .withId(resultSet.getLong("user_id"))
                .withEmail(resultSet.getString("email"))
                .withPassword(resultSet.getString("password"))
                .withName(resultSet.getString("name"))
                .withSurname(resultSet.getString("surname"))
                .withTeamId(resultSet.getLong("team_id"))
                .withCaptain(resultSet.getBoolean("is_captain"))
                .withRole(RoleEntity.valueOf(resultSet.getString("role_name").toUpperCase()))
                .build());
    }

    private void mapPreparedStatementForTeamId(PreparedStatement preparedStatement, UserEntity entity) throws SQLException {
        if (entity.getTeamId() == null) {
            preparedStatement.setNull(6, Types.INTEGER);
        } else {
            preparedStatement.setLong(6, entity.getTeamId());
        }
    }
}
