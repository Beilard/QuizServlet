package ua.quiz.model.dao.impl;

import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.dao.GameDao;
import ua.quiz.model.entity.GameEntity;
import ua.quiz.model.entity.PhaseEntity;
import ua.quiz.model.entity.QuestionEntity;
import ua.quiz.model.entity.StatusEntity;
import ua.quiz.model.exception.DataBaseRuntimeException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDaoImpl extends AbstractCrudDaoImpl<GameEntity> implements GameDao {

    private static final String SAVE_QUERY =
            "INSERT INTO game(number_of_questions, time_per_question, team_id) VALUES (?,?,?)";

    private static final String FIND_BY_ID_QUERY =
            "SELECT *FROM game INNER JOIN status ON status_id = status.status_id" +
                    "INNER JOIN phase ON game_id = phase.game_id" +
                    "INNER JOIN question ON phase.question_id = question.question_id" +
                    "WHERE game.game_id = ?";

    private static final String FIND_ALL_QUERY =
            "SELECT * FROM game INNER JOIN status ON status_id = status.status_id " +
                    "INNER JOIN phase ON game_id = phase.game_id" +
                    "INNER JOIN question ON phase.question_id = question.question_id" +
                    "ORDER BY status.status_id DESC LIMIT ?, ?";

    private static final String UPDATE_QUERY =
            "UPDATE game SET number_of_questions = ?, time_per_question = ?, team_id = ?, status_id = ?, WHERE game.game_id = ?";

    private static final String COUNT_QUERY = "SELECT COUNT(*) AS count FROM game";

    public GameDaoImpl(DBConnector dbConnector) {
        super(dbConnector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, COUNT_QUERY);
    }

    @Override
    public Long saveAndReturnId(GameEntity gameEntity) {
        Long idToReturn = 0L;
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            mapForInsertStatement(preparedStatement, gameEntity);
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()) {
                    idToReturn = resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Insertion has failed, with" + gameEntity.toString(), e);
        }
        return idToReturn;
    }

    @Override
    protected Optional<GameEntity> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Optional.ofNullable(GameEntity.builder()
                .withId(resultSet.getLong("game_id"))
                .withNumberOfQuestions(resultSet.getInt("number_of_questions"))
                .withTimePerQuestion(resultSet.getInt("time_per_question"))
                .withTeamId(resultSet.getLong("team_id"))
                .withStatusEntity(StatusEntity.valueOf(resultSet.getString("status_name").toUpperCase()))
                .withPhaseEntities(mapResultSetToPhaseList(resultSet))
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

    private List<PhaseEntity> mapResultSetToPhaseList(ResultSet resultSet) throws SQLException {
        List<PhaseEntity> phaseEntities = new ArrayList<>();
        resultSet.beforeFirst();
        while (resultSet.next()) {
            phaseEntities.add(mapResultSetToPhaseEntity(resultSet));
        }
        return phaseEntities;
    }

    private PhaseEntity mapResultSetToPhaseEntity(ResultSet resultSet) throws SQLException {
        return PhaseEntity.builder()
                .withId(resultSet.getLong("phase_id"))
                .withQuestion(mapResultSetToQuestionEntity(resultSet))
                .withStartTime(resultSet.getTimestamp("start_time").toLocalDateTime())
                .withStartTime(resultSet.getTimestamp("end_time").toLocalDateTime())
                .withStartTime(resultSet.getTimestamp("deadline").toLocalDateTime())
                .withHintUsed(resultSet.getBoolean("hint_used"))
                .withCorrect(resultSet.getBoolean("is_correct"))
                .withGivenAnswer(resultSet.getString("given_answer"))
                .withGameId(resultSet.getLong("game_id"))
                .build();
    }


    private QuestionEntity mapResultSetToQuestionEntity(ResultSet resultSet) throws SQLException {
        return QuestionEntity.builder()
                .withId(resultSet.getLong("question_id"))
                .withBody(resultSet.getString("body"))
                .withCorrectAnswer(resultSet.getString("correct_answer"))
                .withHint("hint")
                .build();
    }
}
