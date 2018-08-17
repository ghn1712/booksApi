package com.ghn1712.guiabolso.books.gateways;

import com.ghn1712.guiabolso.books.entities.Book;

public interface BooksGateway extends BooksListGateway {

    Book getBook(String id);

    String addBook(Book book);
}
