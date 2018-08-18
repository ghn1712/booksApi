package com.ghn1712.guiabolso.books.entities;


public class Book {

    public Book() {
    }

    public Book(String title, String description, String isbn, String language) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.language = language;
    }

    private String title;
    private String description;
    private String id;
    private String isbn;
    private String language;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getLanguage() {
        return language;
    }
}
