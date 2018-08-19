package com.ghn1712.guiabolso.books.gateways;

import java.util.List;

import org.sql2o.Query;

import com.ghn1712.guiabolso.books.database.DatabaseConnectionHandler;
import com.ghn1712.guiabolso.books.entities.Book;
import com.google.inject.Inject;

public class BooksRepositoryGateway implements BooksGateway {

    DatabaseConnectionHandler connectionHandler;
    private static final String LIST_QUERY = "select * from books";
    private static final String GET_QUERY = "select * from books where id = :id";
    private static final String INSERT_QUERY = "insert into books (title, description, isbn, language) values (:title, :description, :isbn, :language)";
    private static final String TRUNCATE_QUERY = "truncate table books restart identity";

    @Inject
    public BooksRepositoryGateway(DatabaseConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    public List<Book> listBooks() {
        try (Query query = connectionHandler.getConnection().open().createQuery(LIST_QUERY)) {
            return query.executeAndFetch(Book.class);
        }

    }

    @Override
    public Book getBook(String id) {
        try (Query query = connectionHandler.getConnection().open().createQuery(GET_QUERY)) {
            return query.addParameter("id", Integer.parseInt(id)).executeAndFetchFirst(Book.class);
        }
    }

    @Override
    public String addBook(Book book) {
        try (Query query = connectionHandler.getConnection().open().createQuery(INSERT_QUERY, true)) {
            return query.bind(book).executeUpdate().getKey(String.class);
        }
    }

    @Override
    public void truncate() {
        try (Query query = connectionHandler.getConnection().open().createQuery(TRUNCATE_QUERY)) {
            query.executeUpdate();
        }

    }
}
