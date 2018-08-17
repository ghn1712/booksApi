package com.ghn1712.guiabolso.books.gateways;

import java.util.List;

import com.ghn1712.guiabolso.books.entities.Book;


public interface BooksGateway {

    List<Book> listBooks();

    Book getBook(String id);

    String addBook(Book book);
}
