package com.ghn1712.guiabolso.books.controllers;

import java.util.List;

import javax.inject.Inject;

import com.ghn1712.guiabolso.books.entities.Book;
import com.ghn1712.guiabolso.books.usecases.BooksUsecase;

public class BooksControllerImpl implements BooksController {

    BooksUsecase booksUsecase;

    @Inject
    public BooksControllerImpl(BooksUsecase booksUsecase) {
        this.booksUsecase = booksUsecase;
    }

    @Override
    public List<Book> listBooks() {
        return booksUsecase.listBooks();
    }

    @Override
    public Book getBook(String id) {
        return booksUsecase.getBook(id);
    }

    @Override
    public String addBook(Book book) {
        return booksUsecase.addBook(book);
    }
}
