package com.ghn1712.guiabolso.books.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.ghn1712.guiabolso.books.controllers.BooksController;
import com.ghn1712.guiabolso.books.entities.Book;
import com.ghn1712.guiabolso.books.gateways.BooksGateway;
import com.ghn1712.guiabolso.books.injection.modules.BooksModule;
import com.ghn1712.guiabolso.books.usecases.BooksUsecase;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ControllerIntegratedTestsCase {

    BooksController controller;
    BooksUsecase usecase;

    @Before
    public void set_up() {
        Injector injector = Guice.createInjector(new ControllerModule());
        BooksGateway gateway = injector.getInstance(BooksGateway.class);
        gateway.truncate();
    }

    @Test
    public void should_return_empty_list_when_usecase_return_empty_list() {
        Injector injector = Guice.createInjector(new ControllerModule());
        controller = injector.getInstance(BooksController.class);
        assertTrue(controller.listBooks().isEmpty());
    }

    @Test
    public void should_return_list_when_usecase_return_list() {
        Injector injector = Guice.createInjector(new BooksModule());
        controller = injector.getInstance(BooksController.class);
        List<Book> listBooks = controller.listBooks();
        assertFalse(listBooks.isEmpty());
        assertEquals(24, listBooks.size());
    }

    @Test
    public void should_add_book() {
        Book book = new Book("lalala", "lalala", "1", "9870123456789", "en");
        Injector injector = Guice.createInjector(new BooksModule());
        controller = injector.getInstance(BooksController.class);
        assertEquals("1", controller.addBook(book));
    }

    @Test
    public void should_return_empty_optional_when_usecase_returns_null() {
        Injector injector = Guice.createInjector(new ControllerModule());
        controller = injector.getInstance(BooksController.class);
        assertFalse(controller.getBookById("123").isPresent());
    }

    @Test
    public void should_return_book_when_usecase_returns_book() {
        Book book = new Book("lalala", "lalala", "1", "9870123456789", "en");
        Injector injector = Guice.createInjector(new ControllerModule());
        BooksGateway gateway = injector.getInstance(BooksGateway.class);
        gateway.addBook(book);
        controller = injector.getInstance(BooksController.class);
        assertEquals(book, controller.getBookById("1").get());
    }

    @AfterClass
    public static void tear_down() {
        Guice.createInjector(new BooksModule()).getInstance(BooksGateway.class).truncate();
    }
}
