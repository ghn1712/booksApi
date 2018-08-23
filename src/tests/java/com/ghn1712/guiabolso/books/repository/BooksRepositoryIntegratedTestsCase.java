package com.ghn1712.guiabolso.books.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Sql2o;

import com.ghn1712.guiabolso.books.database.DatabaseConnectionHandler;
import com.ghn1712.guiabolso.books.entities.Book;
import com.ghn1712.guiabolso.books.gateways.BooksGateway;
import com.ghn1712.guiabolso.books.injection.modules.BooksModule;
import com.ghn1712.guiabolso.books.injection.modules.InjectorProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class BooksRepositoryIntegratedTestsCase {

    static BooksGateway repository;

    @BeforeClass
    public static void set_up() {
        repository = InjectorProvider.getInjector().getInstance(BooksGateway.class);
    }

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
        repository.truncate();
        Book book = repository.getBook("1");
        assertNull(book);
    }

    @Test
    public void should_add_book() {
        repository.truncate();
        Book book = new Book("title", "description", "1", "isbn", "lg");
        String id = repository.addBook(book);
        Book response = repository.getBook("1");
        assertEquals(book, response);
        assertEquals(id, response.getId());
    }

    @Test
    public void should_return_book_when_book_exists() {
        repository.truncate();
        Book book = new Book("title", "description", "1", "isbn", "lg");
        repository.addBook(book);
        Book response = repository.getBook("1");
        assertEquals(book, response);
    }

    @Test
    public void should_retun_empty_list_when_books_doesnt_exists() {
        repository.truncate();
        assertTrue(repository.listBooks().isEmpty());
    }

    @Test
    public void should_return_books_list_when_exists_books_in_repository() {
        repository.truncate();
        Book book = new Book("title", "description", "1", "isbn", "lg");
        repository.addBook(book);
        repository.addBook(book);
        List<Book> books = repository.listBooks();
        assertEquals(2, books.size());
    }

    @AfterClass
    public static void tear_down() {
        repository.truncate();
    }
}
