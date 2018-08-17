package com.ghn1712.guiabolso.books.controllers;

import java.util.List;
import java.util.Optional;

import com.ghn1712.guiabolso.books.entities.Book;

public interface BooksController {

    List<Book> listBooks();

    Optional<Book> getBookById(String id);

    String addBook(Book book);
}
