package com.ghn1712.guiabolso.books.database;

import javax.inject.Inject;

import org.sql2o.Sql2o;

import com.ghn1712.guiabolso.books.config.DatabaseConfig;

public class DatabaseConnectionHandlerPostgres implements DatabaseConnectionHandler {

    DatabaseConfig config;

    @Inject
    public DatabaseConnectionHandlerPostgres(DatabaseConfig config) {
        this.config = config;
    }

    @Override
    public Sql2o getConnection() {
        return new Sql2o(config.getUrl(), config.getUser(), config.getPassword());
    }
}
