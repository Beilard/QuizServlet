package ua.quiz.model.dao.impl;

import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.dao.PhaseDao;
import ua.quiz.model.entity.PhaseEntity;
import ua.quiz.model.entity.QuestionEntity;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PhaseDaoImpl extends AbstractCrudDaoImpl<PhaseEntity> implements PhaseDao {
    private static final String SAVE_QUERY =
            "INSERT INTO phase(question_id, game_id) VALUES (?,?)";

    private static final String FIND_BY_ID_QUERY =
            "SELECT * FROM phase INNER JOIN question ON question_id = question.question_id WHERE phase.phase_id = ?";

    private static final String FIND_ALL_QUERY =
            "SELECT * FROM phase INNER JOIN question ON question_id = question.question_id ORDER BY phase.phase_id DESC LIMIT ?, ?";

    private static final String UPDATE_QUERY =
            "UPDATE phase SET question_id = ?, game_id = ?, start_time = ?, end_time = ?, deadline = ?, hint_used = ?, is_correct = ?, given_answer = ? WHERE phase.phase_id = ?";

    private static final String COUNT_QUERY = "SELECT COUNT(*) AS count FROM phase";

    private static final String FIND_ALL_BY_GAME_ID = "SELECT * FROM phase INNER JOIN question ON phase.question_id = question.question_id WHERE phase.game_id = ?";

    public PhaseDaoImpl(DBConnector dbConnector) {
        super(dbConnector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, COUNT_QUERY);
    }

    @Override
    public List<PhaseEntity> findPhasesByGameId(Long gameId) {
        return findListByLongParam(gameId, FIND_ALL_BY_GAME_ID);
    }

    @Override
    protected Optional<PhaseEntity> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Optional.ofNullable(PhaseEntity.builder()
                .withId(resultSet.getLong("phase_id"))
                .withQuestion(mapResultSetToQuestionEntity(resultSet))
                .withStartTime(mapResultSetToLocalDatetime(resultSet, "start_time"))
                .withEndTime(mapResultSetToLocalDatetime(resultSet, "end_time"))
                .withDeadline(mapResultSetToLocalDatetime(resultSet, "deadline"))
                .withHintUsed(resultSet.getBoolean("hint_used"))
                .withCorrect(resultSet.getBoolean("is_correct"))
                .withGivenAnswer(resultSet.getString("given_answer"))
                .withGameId(resultSet.getLong("game_id"))
                .build());
    }

    private LocalDateTime mapResultSetToLocalDatetime(ResultSet resultSet, String columnLabel) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(columnLabel);
        return  timestamp == null ? null : timestamp.toLocalDateTime();
    }

    @Override
    protected void mapForInsertStatement(PreparedStatement preparedStatement, PhaseEntity entity) throws SQLException {
        preparedStatement.setLong(1, entity.getQuestion().getId());
        preparedStatement.setLong(2, entity.getGameId());
    }

    @Override
    protected void mapForUpdateStatement(PreparedStatement preparedStatement, PhaseEntity entity) throws SQLException {
        mapForInsertStatement(preparedStatement, entity);
        preparedStatement.setTimestamp(3, Timestamp.valueOf(entity.getStartTime()));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getEndTime()));
        preparedStatement.setTimestamp(5, Timestamp.valueOf(entity.getDeadline()));
        preparedStatement.setBoolean(6, entity.getHintUsed());
        preparedStatement.setBoolean(7, entity.getCorrect());
        preparedStatement.setString(8, entity.getGivenAnswer());
        preparedStatement.setLong(9, entity.getId());
    }

    private QuestionEntity mapResultSetToQuestionEntity(ResultSet resultSet) throws SQLException {
        return QuestionEntity.builder()
                .withId(resultSet.getLong("question_id"))
                .withBody(resultSet.getString("body"))
                .withCorrectAnswer(resultSet.getString("correct_answer"))
                .withHint(resultSet.getString("hint"))
                .build();
    }
}
