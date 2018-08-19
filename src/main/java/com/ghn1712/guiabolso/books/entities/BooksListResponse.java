package com.ghn1712.guiabolso.books.entities;

import java.util.List;

public class BooksListResponse {

    private int numberBooks;
    private List<Book> books;

    public BooksListResponse() {
    }

    public BooksListResponse(int numberBooks, List<Book> books) {
        super();
        this.numberBooks = numberBooks;
        this.books = books;
    }

    public int getNumberBooks() {
        return numberBooks;
    }

    public List<Book> getBooks() {
        return books;
    }
}
