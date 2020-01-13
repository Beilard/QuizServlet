package ua.quiz.model.dao.impl;

import com.sun.xml.internal.bind.v2.TODO;
import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.dao.TeamDao;
import ua.quiz.model.entity.TeamEntity;
import ua.quiz.model.excpetion.DataBaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TeamDaoImpl extends AbstractCrudDaoImpl<TeamEntity> implements TeamDao {
    private static final String SAVE_QUERY = "INSERT INTO team(team_name, captain_id) VALUES (?,?)";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM team WHERE team.team_id = ?";

    private static final String FIND_ALL_PAGINATION_QUERY = "SELECT * FROM team ORDER BY team_id DESC LIMIT ?, ?";

    private static final String UPDATE_QUERY = "UPDATE team SET team_name = ?, captain_id = ?, WHERE team.team_id = ?";

    private static final String FIND_BY_TEAM_NAME = "SELECT * FROM team WHERE team_name = ?";

    private static final String COUNT_QUERY = "SELECT COUNT(*) AS count FROM team";

    private  static final String DELETE_BY_ID = "SELECT * FROM team WHERE team.team_id = ?";

    public TeamDaoImpl(DBConnector dbConnector, String saveQuery, String findByIdQuery, String findAllQuery, String updateQuery) {
        super(dbConnector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_PAGINATION_QUERY, UPDATE_QUERY, COUNT_QUERY);
    }

    public Optional<TeamEntity> findByTeamName(String name) {
        return findByStringParam(name, FIND_BY_TEAM_NAME);
    }

    //TODO: remove
    @Override
    public void deleteById(Long id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Connection was not established", e);
        }
    }

    @Override
    protected Optional<TeamEntity> mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        TeamEntity teamEntity = new TeamEntity(resultSet.getString("team_name"), resultSet.getLong("captain_id"));
        teamEntity.setId(resultSet.getLong("team_id"));

        return Optional.of(teamEntity);
    }

    @Override
    protected void mapForInsertStatement(PreparedStatement preparedStatement, TeamEntity entity) throws SQLException {
        preparedStatement.setString(1, entity.getTeamName());
        preparedStatement.setLong(2, entity.getCaptainId());
    }

    @Override
    protected void mapForUpdateStatement(PreparedStatement preparedStatement, TeamEntity entity) throws SQLException {
        mapForInsertStatement(preparedStatement, entity);
        preparedStatement.setLong(3, entity.getId());
    }
}
