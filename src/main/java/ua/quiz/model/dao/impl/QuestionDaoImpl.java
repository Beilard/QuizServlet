package ua.quiz.model.dao.impl;

import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.dao.QuestionDao;
import ua.quiz.model.dto.Question;
import ua.quiz.model.entity.QuestionEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class QuestionDaoImpl extends AbstractCrudDaoImpl<QuestionEntity> implements QuestionDao {

    private static final String SAVE_QUERY = "INSERT INTO question(body, correct_answer, hint) VALUES (?,?,?)";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM question WHERE question.question_id = ?";

    private static final String FIND_ALL_PAGINATION_QUERY = "SELECT * FROM question ORDER BY question_id DESC LIMIT ?, ?";

    private static final String UPDATE_QUERY = "UPDATE question SET body = ?, correct_answer = ?, hint = ?, WHERE question.question_id = ?";

    public QuestionDaoImpl(DBConnector dbConnector, String saveQuery, String findByIdQuery, String findAllQuery, String updateQuery) {
        super(dbConnector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_PAGINATION_QUERY, UPDATE_QUERY);
    }

    @Override
    protected Optional<QuestionEntity> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Optional.ofNullable(QuestionEntity.builder()
        .withId(resultSet.getLong("question_id"))
        .withBody(resultSet.getString("body"))
        .withCorrectAnswer(resultSet.getString("correct_answer"))
        .withHint("hint")
        .build());
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, QuestionEntity entity) throws SQLException {
        preparedStatement.setString(1, entity.getBody());
        preparedStatement.setString(2, entity.getCorrectAnswer());
        preparedStatement.setString(3, entity.getHint());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, QuestionEntity entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setLong(4, entity.getId());
    }
}
