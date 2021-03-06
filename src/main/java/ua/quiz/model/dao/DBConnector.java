package ua.quiz.model.dao;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ua.quiz.model.exception.DataBaseRuntimeException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnector {
    private static final Logger LOGGER = Logger.getLogger(DBConnector.class);
    private static final BasicDataSource dataSource = new BasicDataSource();

    public DBConnector(String configurationFileName) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(configurationFileName);

        dataSource.setDriverClassName(resourceBundle.getString("db.driver"));
        dataSource.setUrl(resourceBundle.getString("db.url"));
        dataSource.setUsername(resourceBundle.getString("db.user"));
        dataSource.setPassword(resourceBundle.getString("db.password"));
        dataSource.setMinIdle(Integer.parseInt(resourceBundle.getString("db.minIdle")));
        dataSource.setMaxIdle(Integer.parseInt(resourceBundle.getString("db.maxIdle")));
        dataSource.setMaxOpenPreparedStatements(Integer.parseInt(resourceBundle.getString("db.maxPreparedStatements")));
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("There is a connection error to the DB", e);
            throw new DataBaseRuntimeException(e);
        }
    }
}
