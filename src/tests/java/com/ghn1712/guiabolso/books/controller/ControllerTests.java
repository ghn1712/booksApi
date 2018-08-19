package com.ghn1712.guiabolso.books.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ghn1712.guiabolso.books.controllers.BooksController;
import com.ghn1712.guiabolso.books.controllers.BooksControllerImpl;
import com.ghn1712.guiabolso.books.entities.Book;
import com.ghn1712.guiabolso.books.usecases.BooksUsecase;

public class ControllerTests {

    BooksController controller;
    BooksUsecase usecase;

    @Before
    public void set_up() {
        usecase = Mockito.mock(BooksUsecase.class);
        controller = new BooksControllerImpl(usecase);
    }

    @Test
    public void should_return_empty_list_when_usecase_return_empty_list() {
        Mockito.when(usecase.listBooks()).thenReturn(Collections.emptyList());
        assertTrue(controller.listBooks().isEmpty());
        Mockito.verify(usecase, Mockito.times(1)).listBooks();
    }

    @Test
    public void should_return_list_when_usecase_return_list() {
        Book book1 = new Book("lalala", "lalala", "9870123456789", "en");
        Book book2 = new Book("lorem ipsum", "lorem ipsum", "9870123456789", "en");
        Mockito.when(usecase.listBooks()).thenReturn(new ArrayList<>(Arrays.asList(book1, book2)));
        List<Book> listBooks = controller.listBooks();
        assertFalse(listBooks.isEmpty());
        Mockito.verify(usecase, Mockito.times(1)).listBooks();
        assertEquals(2, listBooks.size());
        assertTrue(listBooks.contains(book1));
        assertTrue(listBooks.contains(book2));
    }

    @Test
    public void should_add_book() {
        Book book = new Book("lalala", "lalala", "9870123456789", "en");
        Mockito.when(usecase.addBook(book)).thenReturn("198020");
        assertEquals("198020", controller.addBook(book));
        Mockito.verify(usecase, Mockito.times(1)).addBook(book);
    }

    @Test
    public void should_return_empty_optional_when_usecase_returns_null() {
        Mockito.when(usecase.getBook("123")).thenReturn(Optional.empty());
        assertFalse(controller.getBookById("123").isPresent());
        Mockito.verify(usecase, Mockito.times(1)).getBook("123");
    }

    @Test
    public void should_return_book_when_usecase_returns_book() {
        Book book = new Book("lalala", "lalala", "123", "9870123456789", "en");
        Mockito.when(usecase.getBook("123")).thenReturn(Optional.of(book));
        assertEquals(book, controller.getBookById("123").get());
        Mockito.verify(usecase, Mockito.times(1)).getBook("123");
    }
}
