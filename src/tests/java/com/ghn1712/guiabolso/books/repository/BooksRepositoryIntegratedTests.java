package com.ghn1712.guiabolso.books.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.sql2o.Sql2o;

import com.ghn1712.guiabolso.books.database.DatabaseConnectionHandler;
import com.ghn1712.guiabolso.books.entities.Book;
import com.ghn1712.guiabolso.books.gateways.BooksGateway;
import com.ghn1712.guiabolso.books.injection.modules.BooksModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class BooksRepositoryIntegratedTests {

    BooksGateway repository;

    @Test
    public void should_connection_handler_be_singleton() {
        Injector injector = Guice.createInjector(new BooksModule());
        DatabaseConnectionHandler connectionHandler1 = injector.getInstance(DatabaseConnectionHandler.class);
        DatabaseConnectionHandler connectionHandler2 = injector.getInstance(DatabaseConnectionHandler.class);
        assertTrue(connectionHandler1 == connectionHandler2);
    }

    @Test
    public void should_connection_be_singleton() {
        Injector injector = Guice.createInjector(new BooksModule());
        DatabaseConnectionHandler connectionHandler1 = injector.getInstance(DatabaseConnectionHandler.class);
        Sql2o connection1 = connectionHandler1.getConnection();
        DatabaseConnectionHandler connectionHandler2 = injector.getInstance(DatabaseConnectionHandler.class);
        Sql2o connection2 = connectionHandler2.getConnection();
        assertTrue(connection1 == connection2);
    }

    @Test
    public void should_return_null_when_book_doesnt_exist() {
        Injector injector = Guice.createInjector(new BooksModule());
        repository = injector.getInstance(BooksGateway.class);
        repository.truncate();
        Book book = repository.getBook("1");
        assertNull(book);
    }

    @Test
    public void should_add_book() {
        Injector injector = Guice.createInjector(new BooksModule());
        repository = injector.getInstance(BooksGateway.class);
        repository.truncate();
        Book book = new Book("title", "description", "1", "isbn", "lg");
        String id = repository.addBook(book);
        Book response = repository.getBook("1");
        assertEquals(book, response);
        assertEquals(id, response.getId());
    }

    @Test
    public void should_return_book_when_book_exists() {
        Injector injector = Guice.createInjector(new BooksModule());
        repository = injector.getInstance(BooksGateway.class);
        repository.truncate();
        Book book = new Book("title", "description", "1", "isbn", "lg");
        repository.addBook(book);
        Book response = repository.getBook("1");
        assertEquals(book, response);
    }

    @Test
    public void should_retun_empty_list_when_books_doesnt_exists() {
        Injector injector = Guice.createInjector(new BooksModule());
        repository = injector.getInstance(BooksGateway.class);
        repository.truncate();
        assertTrue(repository.listBooks().isEmpty());
    }

    @Test
    public void should_return_books_list_when_exists_books_in_repository() {
        Injector injector = Guice.createInjector(new BooksModule());
        repository = injector.getInstance(BooksGateway.class);
        repository.truncate();
        Book book = new Book("title", "description", "1", "isbn", "lg");
        repository.addBook(book);
        repository.addBook(book);
        List<Book> books = repository.listBooks();
        assertEquals(2, books.size());
    }
}
