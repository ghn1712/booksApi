package com.ghn1712.guiabolso.books.usecase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ghn1712.guiabolso.books.entities.Book;
import com.ghn1712.guiabolso.books.gateways.BooksGateway;
import com.ghn1712.guiabolso.books.gateways.BooksListGateway;
import com.ghn1712.guiabolso.books.usecases.BooksUsecase;
import com.ghn1712.guiabolso.books.usecases.BooksUsecaseImpl;

public class UsecaseTests {

    BooksUsecase usecase;
    BooksGateway gateway;
    BooksListGateway listGateway;

    @Before
    public void set_up() {
        gateway = Mockito.mock(BooksGateway.class);
        listGateway = Mockito.mock(BooksListGateway.class);
        usecase = new BooksUsecaseImpl(gateway, listGateway);
    }

    @Test
    public void should_return_empty_list_when_both_gateways_return_empty_list() {
        Mockito.when(gateway.listBooks()).thenReturn(new ArrayList<>());
        Mockito.when(listGateway.listBooks()).thenReturn(new ArrayList<>());
        assertTrue(usecase.listBooks().isEmpty());
        Mockito.verify(gateway, Mockito.times(1)).listBooks();
        Mockito.verify(listGateway, Mockito.times(1)).listBooks();
    }

    @Test
    public void should_return_list_only_from_gateway() {
        Book book1 = new Book("lalala", "lalala", "9870123456789", "en");
        Book book2 = new Book("lorem ipsum", "lorem ipsum", "9870123456789", "en");
        Mockito.when(gateway.listBooks()).thenReturn(Arrays.asList(book1, book2));
        Mockito.when(listGateway.listBooks()).thenReturn(new ArrayList<>());
        List<Book> listBooks = usecase.listBooks();
        assertFalse(listBooks.isEmpty());
        Mockito.verify(gateway, Mockito.times(1)).listBooks();
        Mockito.verify(listGateway, Mockito.times(1)).listBooks();
        assertEquals(2, listBooks.size());
        assertTrue(listBooks.contains(book1));
        assertTrue(listBooks.contains(book2));
    }

    @Test
    public void should_return_list_when_both_gateways_return_list() {
        Book book1 = new Book("lalala", "lalala", "9870123456789", "en");
        Book book2 = new Book("lorem ipsum", "lorem ipsum", "9870123456789", "en");
        Mockito.when(gateway.listBooks()).thenReturn(new ArrayList<>(Arrays.asList(book1)));
        Mockito.when(listGateway.listBooks()).thenReturn(new ArrayList<>(Arrays.asList(book2)));
        List<Book> listBooks = usecase.listBooks();
        assertFalse(listBooks.isEmpty());
        Mockito.verify(gateway, Mockito.times(1)).listBooks();
        Mockito.verify(listGateway, Mockito.times(1)).listBooks();
        assertEquals(2, listBooks.size());
        assertTrue(listBooks.contains(book1));
        assertTrue(listBooks.contains(book2));
    }

    @Test
    public void should_return_list_only_from_list_gateway() {
        Book book1 = new Book("lalala", "lalala", "9870123456789", "en");
        Book book2 = new Book("lorem ipsum", "lorem ipsum", "9870123456789", "en");
        Mockito.when(gateway.listBooks()).thenReturn(new ArrayList<>());
        Mockito.when(listGateway.listBooks()).thenReturn(Arrays.asList(book1, book2));
        List<Book> listBooks = usecase.listBooks();
        assertFalse(listBooks.isEmpty());
        Mockito.verify(gateway, Mockito.times(1)).listBooks();
        Mockito.verify(listGateway, Mockito.times(1)).listBooks();
        assertEquals(2, listBooks.size());
        assertTrue(listBooks.contains(book1));
        assertTrue(listBooks.contains(book2));
    }

    @Test
    public void should_add_book() {
        Book book = new Book("lalala", "lalala", "9870123456789", "en");
        Mockito.when(gateway.addBook(book)).thenReturn("198020");
        assertEquals("198020", usecase.addBook(book));
        Mockito.verify(gateway, Mockito.times(1)).addBook(book);
    }

    @Test
    public void should_return_empty_optional_when_repository_returns_null() {
        Mockito.when(gateway.getBook("123")).thenReturn(null);
        assertFalse(usecase.getBook("123").isPresent());
        Mockito.verify(gateway, Mockito.times(1)).getBook("123");
    }

    @Test
    public void should_return_book_when_repository_returns_book() {
        Book book = new Book("lalala", "lalala", "123", "9870123456789", "en");
        Mockito.when(gateway.getBook("123")).thenReturn(book);
        assertEquals(book, usecase.getBook("123").get());
        Mockito.verify(gateway, Mockito.times(1)).getBook("123");
    }
}
