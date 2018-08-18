package com.ghn1712.guiabolso.books.crawler;

public interface IsbnRetrieverStrategy {

    default String execute(String url) {
        return "Unavailable";
    }
}
