package com.ghn1712.guiabolso.books.crawler;

public interface IsbnStrategy {

    default String execute(String url) {
        return "undefined";
    }
}
