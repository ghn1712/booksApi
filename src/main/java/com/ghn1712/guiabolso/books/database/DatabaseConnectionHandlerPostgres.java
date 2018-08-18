package com.ghn1712.guiabolso.books.database;

import javax.inject.Inject;

import org.sql2o.Sql2o;

import com.ghn1712.guiabolso.books.config.DatabaseConfig;

public class DatabaseConnectionHandlerPostgres implements DatabaseConnectionHandler {

    private final Sql2o connection;

    @Inject
    public DatabaseConnectionHandlerPostgres(DatabaseConfig config) {
        connection = createConnection(config);
    }

    @Override
    public Sql2o getConnection() {
        return connection;
    }

    private Sql2o createConnection(DatabaseConfig config) {
        return new Sql2o(config.getUrl(), config.getUser(), config.getPassword());

    }
}
