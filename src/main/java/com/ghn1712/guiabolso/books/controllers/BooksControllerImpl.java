package com.ghn1712.guiabolso.books.controllers;

import java.util.List;

import javax.inject.Inject;

import com.ghn1712.guiabolso.books.entities.Book;
import com.ghn1712.guiabolso.books.usecases.BooksUsecase;

public class BooksControllerImpl {

    BooksUsecase booksUsecase;

    @Inject
    public BooksControllerImpl(BooksUsecase booksUsecase) {
        this.booksUsecase = booksUsecase;
    }

    public List<Book> listBooks() {
        return booksUsecase.listBooks();
    }

    public Book getBook(String id) {
        return booksUsecase.getBook(id);
    }

    public String addBook(Book book) {
        return booksUsecase.addBook(book);
    }

}
