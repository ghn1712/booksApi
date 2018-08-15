package com.ghn1712.guiabolso.books.usecases;

import java.util.List;

import com.ghn1712.guiabolso.books.entities.Book;

public interface BooksUsecase {

    List<Book> listBooks();

    Book getBook(String id);

    void addBook(Book book);
}
