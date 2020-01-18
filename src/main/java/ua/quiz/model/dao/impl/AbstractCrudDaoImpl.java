package ua.quiz.model.dao.impl;

import org.apache.log4j.Logger;
import ua.quiz.model.dao.CrudDao;
import ua.quiz.model.dao.DBConnector;
import ua.quiz.model.exception.DataBaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E, Long> {
    private static final Logger LOGGER = Logger.getLogger(AbstractCrudDaoImpl.class);

    private static final BiConsumer<PreparedStatement, String> STRING_CONSUMER
            = (PreparedStatement pr, String param) -> {
        try {
            pr.setString(1, param);
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    };

    private static final BiConsumer<PreparedStatement, Long> LONG_CONSUMER
            = (PreparedStatement pr, Long param) -> {
        try {
            pr.setLong(1, param);
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    };

    protected final DBConnector dbConnector;
    private final String saveQuery;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String updateQuery;
    private final String countQuery;

    public AbstractCrudDaoImpl(DBConnector dbConnector, String saveQuery, String findByIdQuery,
                               String findAllQuery, String updateQuery, String countQuery) {
        this.dbConnector = dbConnector;
        this.saveQuery = saveQuery;
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.updateQuery = updateQuery;
        this.countQuery = countQuery;

    }

    @Override
    public void save(E entity) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)) {

            mapForInsertStatement(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Insertion has failed", e);
            throw new DataBaseRuntimeException("Insertion has failed, with" + entity.toString(), e);
        }
    }

    @Override
    public Optional<E> findById(Long id) {
        return findByLongParam(id, findByIdQuery);
    }

    @Override
    public List<E> findAll(Integer startFrom, Integer rowCount) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {

            preparedStatement.setInt(1, startFrom);
            preparedStatement.setInt(2, rowCount);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<E> entities = new ArrayList<>();
                while (resultSet.next()) {
                    mapResultSetToEntity(resultSet).ifPresent(entities::add);
                }
                return entities;
            }
        } catch (SQLException e) {
            LOGGER.error("Connection has not been established", e);
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public Long countEntries() {
        return countEntriesByQuery(countQuery);
    }

    protected Long countEntriesByQuery(String query){
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? resultSet.getLong("count") : 0;
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

        @Override
    public void update(E entity) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            mapForUpdateStatement(preparedStatement, entity);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Update has failed, while updating " + entity.toString(), e);
            throw new DataBaseRuntimeException(e);
        }
    }


    protected Optional<E> findByLongParam(Long id, String query) {
        return findByParam(id, query, LONG_CONSUMER);
    }

    protected Optional<E> findByStringParam(String param, String query) {
        return findByParam(param, query, STRING_CONSUMER);
    }

    protected List<E> findListByStringParam(String param, String query) {
        return findListByParam(param, query, STRING_CONSUMER);
    }

    protected List<E> findListByLongParam(Long param, String query) {
        return findListByParam(param, query, LONG_CONSUMER);
    }

    private <P> Optional<E> findByParam(P param, String query, BiConsumer<PreparedStatement, P> consumer) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            consumer.accept(preparedStatement, param);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? mapResultSetToEntity(resultSet) : Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error("An error has occurred while finding by parameter " + param.toString(), e);
            throw new DataBaseRuntimeException(e);
        }
    }

    private <P> List<E> findListByParam(P param, String query, BiConsumer<PreparedStatement, P> consumer) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            consumer.accept(preparedStatement, param);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<E> entities = new ArrayList<>();
                while (resultSet.next()) {
                    mapResultSetToEntity(resultSet).ifPresent(entities::add);
                }
                return entities;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed operation", e);
            throw new DataBaseRuntimeException(e);
        }
    }

    protected abstract Optional<E> mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void mapForInsertStatement(PreparedStatement preparedStatement, E entity) throws SQLException;

    protected abstract void mapForUpdateStatement(PreparedStatement preparedStatement, E entity) throws SQLException;
}
