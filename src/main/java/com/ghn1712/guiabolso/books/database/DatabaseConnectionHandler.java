package com.ghn1712.guiabolso.books.database;

import org.sql2o.Sql2o;

public interface DatabaseConnectionHandler {

    Sql2o getConnection();
}
