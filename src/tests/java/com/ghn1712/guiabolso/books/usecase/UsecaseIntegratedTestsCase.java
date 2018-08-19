package com.ghn1712.guiabolso.books.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.ghn1712.guiabolso.books.entities.Book;
import com.ghn1712.guiabolso.books.gateways.BooksGateway;
import com.ghn1712.guiabolso.books.injection.modules.BooksModule;
import com.ghn1712.guiabolso.books.usecases.BooksUsecase;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class UsecaseIntegratedTestsCase {

    BooksUsecase usecase;


    @Test
    public void should_return_empty_list_when_both_gateways_return_empty_list() {
        Injector injector = Guice.createInjector(new UsecaseModule());
        usecase = injector.getInstance(BooksUsecase.class);
        BooksGateway gateway = injector.getInstance(BooksGateway.class);
        gateway.truncate();
        List<Book> listBooks = usecase.listBooks();
        assertEquals(0, listBooks.size());

    }

    @Test
    public void should_return_only_list_from_gateway() {
        Injector injector = Guice.createInjector(new UsecaseModule());
        usecase = injector.getInstance(BooksUsecase.class);
        BooksGateway gateway = injector.getInstance(BooksGateway.class);
        gateway.truncate();
        Book book = new Book("title", "description", "1", "isbn", "lg");
        gateway.addBook(book);
        List<Book> listBooks = usecase.listBooks();
        assertEquals(1, listBooks.size());
        assertTrue(listBooks.contains(book));

    }

    @Test
    public void should_return_list_when_both_gateways_return_list() {
        Injector injector = Guice.createInjector(new BooksModule());
        usecase = injector.getInstance(BooksUsecase.class);
        BooksGateway gateway = injector.getInstance(BooksGateway.class);
        gateway.truncate();
        Book book = new Book("title", "description", "1", "isbn", "lg");
        gateway.addBook(book);
        List<Book> listBooks = usecase.listBooks();
        assertEquals(25, listBooks.size());
        assertTrue(listBooks.contains(book));
    }

    @Test
    public void should_only_return_list_from_list_gateway() {
        Injector injector = Guice.createInjector(new BooksModule());
        usecase = injector.getInstance(BooksUsecase.class);
        BooksGateway gateway = injector.getInstance(BooksGateway.class);
        gateway.truncate();
        List<Book> listBooks = usecase.listBooks();
        assertEquals(24, listBooks.size());
    }

    @Test
    public void should_add_book() {
        Injector injector = Guice.createInjector(new UsecaseModule());
        usecase = injector.getInstance(BooksUsecase.class);
        BooksGateway gateway = injector.getInstance(BooksGateway.class);
        gateway.truncate();
        Book book = new Book("title", "description", "1", "isbn", "lg");
        usecase.addBook(book);
        List<Book> booksList = usecase.listBooks();
        assertEquals(1, booksList.size());
        assertTrue(booksList.contains(book));
        assertEquals(book, usecase.getBook("1").get());
    }

    @Test
    public void should_return_empty_optional_when_repository_returns_null() {
        Injector injector = Guice.createInjector(new UsecaseModule());
        usecase = injector.getInstance(BooksUsecase.class);
        BooksGateway gateway = injector.getInstance(BooksGateway.class);
        gateway.truncate();
        assertFalse(usecase.getBook("1").isPresent());
    }

    @Test
    public void should_return_book_when_repository_returns_book() {
        Injector injector = Guice.createInjector(new UsecaseModule());
        usecase = injector.getInstance(BooksUsecase.class);
        BooksGateway gateway = injector.getInstance(BooksGateway.class);
        gateway.truncate();
        Book book = new Book("title", "description", "1", "isbn", "lg");
        usecase.addBook(book);
        assertEquals(book, usecase.getBook("1").get());
    }
}
