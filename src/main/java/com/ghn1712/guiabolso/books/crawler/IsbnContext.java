package com.ghn1712.guiabolso.books.crawler;

public class IsbnContext {

    private static IsbnStrategy strategy;

    private IsbnContext() {
    }

    public static String getIsbn(String url) {
        return strategy.execute(url);
    }

    public static void setStrategy(IsbnStrategy isbnStrategy) {
        strategy = isbnStrategy;
    }
}
