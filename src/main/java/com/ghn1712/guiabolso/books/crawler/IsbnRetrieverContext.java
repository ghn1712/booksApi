package com.ghn1712.guiabolso.books.crawler;

public class IsbnRetrieverContext {


    private IsbnRetrieverContext() {
    }

    public static String getIsbn(String url, IsbnRetrieverStrategy strategy) {
        return strategy.execute(url);
    }

}
