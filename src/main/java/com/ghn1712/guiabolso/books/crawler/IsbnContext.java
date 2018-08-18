package com.ghn1712.guiabolso.books.crawler;

public class IsbnContext {


    private IsbnContext() {
    }
    public static String getIsbn(String url, IsbnStrategy strategy) {
        return strategy.execute(url);
    }

}
