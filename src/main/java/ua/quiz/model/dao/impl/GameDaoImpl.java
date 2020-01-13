package ua.quiz.model.dao.impl;

import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.dao.GameDao;
import ua.quiz.model.entity.GameEntity;
import ua.quiz.model.entity.StatusEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class GameDaoImpl extends AbstractCrudDaoImpl<GameEntity> implements GameDao {

    private static final String SAVE_QUERY = "INSERT INTO game(number_of_questions, time_per_question, team_id) VALUES (?,?,?)";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM game INNER JOIN status ON status_id = status.status_id WHERE status.status_id = ?";

    private static final String FIND_ALL_QUERY = "SELECT * FROM game INNER JOIN status ON status_id = status.status_id ORDER BY status.status_id DESC LIMIT ?, ?";

    private static final String UPDATE_QUERY = "UPDATE game SET number_of_questions = ?, time_per_question = ?, team_id = ?, status_id = ?, WHERE game.game_id = ?";

    private static final String COUNT_QUERY = "SELECT COUNT(*) AS count FROM game";

    public GameDaoImpl(DBConnector dbConnector, String saveQuery, String findByIdQuery, String findAllQuery, String updateQuery) {
        super(dbConnector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, COUNT_QUERY);
    }

    @Override
    protected Optional<GameEntity> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Optional.ofNullable(GameEntity.builder()
        .withId(resultSet.getLong("game_id"))
        .withNumberOfQuestions(resultSet.getInt("number_of_questions"))
        .withTimePerQuestion(resultSet.getInt("time_per_question"))
        .withTeamId(resultSet.getLong("team_id"))
        .withStatusEntity(StatusEntity.valueOf(resultSet.getString("status_name").toUpperCase()))
        .build());
    }

    @Override
    protected void mapForInsertStatement(PreparedStatement preparedStatement, GameEntity entity) throws SQLException {
        preparedStatement.setInt(1, entity.getNumberOfQuestions());
        preparedStatement.setInt(2, entity.getTimePerQuestion());
        preparedStatement.setLong(3, entity.getTeamId());
    }

    @Override
    protected void mapForUpdateStatement(PreparedStatement preparedStatement, GameEntity entity) throws SQLException {
        mapForInsertStatement(preparedStatement, entity);
        preparedStatement.setLong(5, entity.getId());
    }
}
