package ua.quiz.model.dao.impl;

import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.dao.TeamDao;
import ua.quiz.model.entity.TeamEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TeamDaoImpl extends AbstractCrudDaoImpl<TeamEntity> implements TeamDao {
    private static final String SAVE_QUERY = "INSERT INTO team(team_name, captain_id) VALUES (?,?)";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM team WHERE team.team_id = ?";

    private static final String FIND_ALL_PAGINATION_QUERY = "SELECT * FROM team ORDER BY team_id DESC LIMIT ?, ?";

    private static final String UPDATE_QUERY = "UPDATE team SET team_name = ?, captain_id = ?, WHERE question.question_id = ?";

    public TeamDaoImpl(DBConnector dbConnector, String saveQuery, String findByIdQuery, String findAllQuery, String updateQuery) {
        super(dbConnector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_PAGINATION_QUERY, UPDATE_QUERY);
    }


    @Override
    protected Optional<TeamEntity> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Optional.of(new TeamEntity(resultSet.getLong("team_id"),
                resultSet.getString("team_name"), resultSet.getLong("captain_id")));
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, TeamEntity entity) throws SQLException {
        preparedStatement.setString(1, entity.getTeamName());
        preparedStatement.setLong(2, entity.getCaptainId());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, TeamEntity entity) throws SQLException {
        insert(preparedStatement, entity);
        preparedStatement.setLong(3, entity.getId());
    }
}
