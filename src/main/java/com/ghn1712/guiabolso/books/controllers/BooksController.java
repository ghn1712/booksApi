package com.ghn1712.guiabolso.books.controllers;

import java.util.List;

import com.ghn1712.guiabolso.books.entities.Book;

public interface BooksController {

    List<Book> listBooks();

    Book getBook(String id);

    String addBook(Book book);
}
