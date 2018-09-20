package com.ghn1712.guiabolso.books.crawler;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.jsoup.Jsoup;

public class IsbnRetriever {

    private IsbnRetriever() {
    }

    public static String getIsbn(String url) {
        try {
            String isbn = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0").get()
                    .select(":matchesOwn([0-9]{3}(-)?[0-9]{10})").eachText().parallelStream().findAny()
                    .orElse("Unavailable");
            String[] isbnSplitted = isbn.split(" ");
            if (isbnSplitted.length == 2) {
                isbn = isbnSplitted[1];
            }
            return isbn;
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
