package com.ghn1712.guiabolso.books.usecases;

import java.util.List;
import java.util.Optional;

import com.ghn1712.guiabolso.books.entities.Book;

public interface BooksUsecase {

    List<Book> listBooks();

    Optional<Book> getBook(String id);

    String addBook(Book book);
}
