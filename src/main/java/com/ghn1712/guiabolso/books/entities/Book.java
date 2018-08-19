package com.ghn1712.guiabolso.books.entities;

import java.util.Objects;

public class Book {

    public Book() {
    }


    public Book(String title, String description, String isbn, String language) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.language = language;
    }

    public Book(String title, String description, String id, String isbn, String language) {
        this.title = title;
        this.description = description;
        this.id = id;
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
    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, isbn, language);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Book)) {
            return false;
        }
        Book other = (Book) obj;
        return Objects.equals(title, other.title) && Objects.equals(description, other.description)
                && Objects.equals(id, other.id) && Objects.equals(isbn, other.isbn)
                && Objects.equals(language, other.language);
    }
}
