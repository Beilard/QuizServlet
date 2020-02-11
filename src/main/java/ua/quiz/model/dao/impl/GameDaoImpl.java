package ua.quiz.model.dao.impl;

import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.dao.GameDao;
import ua.quiz.model.entity.GameEntity;
import ua.quiz.model.entity.PhaseEntity;
import ua.quiz.model.entity.QuestionEntity;
import ua.quiz.model.entity.StatusEntity;
import ua.quiz.model.exception.DataBaseRuntimeException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDaoImpl extends AbstractCrudDaoImpl<GameEntity> implements GameDao {

    private static final String SAVE_QUERY =
            "INSERT INTO game(number_of_questions, time_per_question, team_id) VALUES (?,?,?)";

    private static final String FIND_BY_ID_QUERY =
            "SELECT * FROM game INNER JOIN status ON game.status_id = status.status_id" +
                    " INNER JOIN phase ON game.game_id = phase.game_id " +
                    "INNER JOIN question ON phase.question_id = question.question_id " +
                    "WHERE game.game_id = ?";

    private static final String FIND_ALL_QUERY =
            "SELECT * FROM game INNER JOIN status ON game.status_id = status.status_id ORDER BY game.game_id DESC LIMIT ?, ?";

    private static final String UPDATE_QUERY =
            "UPDATE game SET number_of_questions = ?, time_per_question = ?, " +
                    "team_id = ?, current_phase =?, status_id = ? WHERE game.game_id = ?";

    private static final String COUNT_QUERY = "SELECT COUNT(*) AS count FROM game";

    private static final String FIND_ALL_BY_TEAM_ID_QUERY =
            "SELECT * FROM game INNER JOIN status ON game.status_id = status.status_id WHERE game.team_id = ? ORDER BY game.game_id DESC LIMIT ?, ?";

    private static final String COUNT_BY_TEAM_ID = "SELECT COUNT(*) AS count FROM game WHERE game.team_id = ?";


    public GameDaoImpl(DBConnector dbConnector) {
        super(dbConnector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, COUNT_QUERY);
    }

    @Override
    public Long saveAndReturnId(GameEntity gameEntity) {
        long idToReturn = 0L;
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            mapForInsertStatement(preparedStatement, gameEntity);
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    idToReturn = resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Insertion has failed, with" + gameEntity.toString(), e);
        }
        return idToReturn;
    }

    @Override
    public Long countAllByTeamId(Long teamId) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_BY_TEAM_ID)) {
            preparedStatement.setLong(1, teamId);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? resultSet.getLong("count") : 0;
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public List<GameEntity> findAll(Long startFrom, Long rowCount) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {

            preparedStatement.setLong(1, startFrom);
            preparedStatement.setLong(2, rowCount);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<GameEntity> entities = new ArrayList<>();
                while (resultSet.next()) {
                    mapResultSetToEntityForFindAll(resultSet).ifPresent(entities::add);
                }
                return entities;
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public List<GameEntity> findAllByTeamId(Long teamId, Long startFrom, Long rowCount) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_TEAM_ID_QUERY)) {

            preparedStatement.setLong(1, teamId);
            preparedStatement.setLong(2, startFrom);
            preparedStatement.setLong(3, rowCount);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<GameEntity> entities = new ArrayList<>();
                while (resultSet.next()) {
                    mapResultSetToEntityForFindAll(resultSet).ifPresent(entities::add);
                }
                return entities;
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    protected Optional<GameEntity> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Optional.ofNullable(GameEntity.builder()
                .withId(resultSet.getLong("game_id"))
                .withNumberOfQuestions(resultSet.getInt("number_of_questions"))
                .withTimePerQuestion(resultSet.getInt("time_per_question"))
                .withCurrentPhase(resultSet.getInt("current_phase"))
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
        preparedStatement.setLong(4, entity.getCurrentPhase());
        preparedStatement.setInt(5, entity.getStatusEntity().ordinal() + 1);
        preparedStatement.setLong(6, entity.getId());
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
                .withStartTime(mapResultSetToLocalDatetime(resultSet, "start_time"))
                .withEndTime(mapResultSetToLocalDatetime(resultSet, "end_time"))
                .withDeadline(mapResultSetToLocalDatetime(resultSet, "deadline"))
                .withHintUsed(resultSet.getBoolean("hint_used"))
                .withCorrect(resultSet.getBoolean("is_correct"))
                .withGivenAnswer(resultSet.getString("given_answer"))
                .withGameId(resultSet.getLong("game_id"))
                .build();
    }

    private LocalDateTime mapResultSetToLocalDatetime(ResultSet resultSet, String columnLabel) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(columnLabel);
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }


    private QuestionEntity mapResultSetToQuestionEntity(ResultSet resultSet) throws SQLException {
        return QuestionEntity.builder()
                .withId(resultSet.getLong("question_id"))
                .withBody(resultSet.getString("body"))
                .withCorrectAnswer(resultSet.getString("correct_answer"))
                .withHint(resultSet.getString("hint"))
                .build();
    }

    private Optional<GameEntity> mapResultSetToEntityForFindAll(ResultSet resultSet) throws SQLException {
        return Optional.ofNullable(GameEntity.builder()
                .withId(resultSet.getLong("game_id"))
                .withNumberOfQuestions(resultSet.getInt("number_of_questions"))
                .withTimePerQuestion(resultSet.getInt("time_per_question"))
                .withCurrentPhase(resultSet.getInt("current_phase"))
                .withTeamId(resultSet.getLong("team_id"))
                .withStatusEntity(StatusEntity.valueOf(resultSet.getString("status_name").toUpperCase()))
                .build());
    }

}
