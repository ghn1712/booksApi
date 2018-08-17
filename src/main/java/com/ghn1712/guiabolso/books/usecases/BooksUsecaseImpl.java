package com.ghn1712.guiabolso.books.usecases;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.ghn1712.guiabolso.books.entities.Book;
import com.ghn1712.guiabolso.books.gateways.BooksGateway;
import com.ghn1712.guiabolso.books.gateways.BooksListGateway;

public class BooksUsecaseImpl implements BooksUsecase {

    BooksGateway booksGateway;
    BooksListGateway booksListGateway;

    @Inject
    public BooksUsecaseImpl(BooksGateway booksGateway, BooksListGateway booksListGateway) {
        this.booksGateway = booksGateway;
        this.booksListGateway = booksListGateway;
    }

    @Override
    public List<Book> listBooks() {
        List<Book> books = booksGateway.listBooks();
        books.addAll(booksListGateway.listBooks());
        return books;
    }

    @Override
    public Optional<Book> getBook(String id) {
        return Optional.ofNullable(booksGateway.getBook(id));
    }

    @Override
    public String addBook(Book book) {
        return booksGateway.addBook(book);
    }
}
