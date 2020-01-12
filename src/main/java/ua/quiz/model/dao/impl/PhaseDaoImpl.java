package ua.quiz.model.dao.impl;

import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.dao.PhaseDao;
import ua.quiz.model.entity.PhaseEntity;
import ua.quiz.model.entity.QuestionEntity;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PhaseDaoImpl extends AbstractCrudDaoImpl<PhaseEntity> implements PhaseDao {
    private static final String SAVE_QUERY = "INSERT INTO phase(question_id, start_time, end_time, deadline, hint_used, is_correct, given_answer, game_id) VALUES (?,?,?,?,?,?,?,?)";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM phase INNER JOIN question ON question_id = question.question_id WHERE question.question_id = ?";

    private static final String FIND_ALL_QUERY = "SELECT * FROM phase INNER JOIN question ON question_id = question.question_id ORDER BY question.question_id DESC LIMIT ?, ?";

    private static final String UPDATE_QUERY = "UPDATE game SET question_id = ?, start_time = ?, end_time = ?, deadline = ?, hint_used = ?, is_correct = ?, given_answer = ?, game_id = ?, WHERE phase.phase_id = ?";

    public PhaseDaoImpl(DBConnector dbConnector, String saveQuery, String findByIdQuery, String findAllQuery, String updateQuery) {
        super(dbConnector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY);
    }


    @Override
    protected Optional<PhaseEntity> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Optional.ofNullable(PhaseEntity.builder()
                .withId(resultSet.getLong("phase_id"))
                .withQuestion(QuestionEntity.builder()
                        .withId(resultSet.getLong("question_id"))
                        .withBody(resultSet.getString("body"))
                        .withCorrectAnswer(resultSet.getString("correct_answer"))
                        .withHint("hint")
                        .build())
                .withStartTime(resultSet.getDate("start_time").toLocalDate())
                .withStartTime(resultSet.getDate("end_time").toLocalDate())
                .withStartTime(resultSet.getDate("deadline").toLocalDate())
                .withHintUsed(resultSet.getBoolean("hint_used"))
                .withCorrect(resultSet.getBoolean("is_correct"))
                .withGivenAnswer(resultSet.getString("given_answer"))
                .withGameId(resultSet.getLong("game_id"))
                .build());
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, PhaseEntity entity) throws SQLException {
        preparedStatement.setLong(1, entity.getQuestion().getId());
        preparedStatement.setDate(2, Date.valueOf(entity.getStartTime()));
        preparedStatement.setDate(3, Date.valueOf(entity.getEndTime()));
        preparedStatement.setDate(4, Date.valueOf(entity.getDeadline()));
        preparedStatement.setBoolean(5, entity.getHintUsed());
        preparedStatement.setBoolean(6, entity.getCorrect());
        preparedStatement.setString(7, entity.getGivenAnswer());
        preparedStatement.setLong(8, entity.getGameId());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, PhaseEntity entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setLong(9, entity.getId());
    }
}
