package com.ghn1712.guiabolso.books.crawler;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

public class PacktpubStrategy implements IsbnRetrieverStrategy {

    private static final String ISBN_REGEX = "^[0-9]{13}$";

    @Override
    public String execute(String url) {
        try {
            return Jsoup.connect(url).get().select("div.book-info-isbn13 span[itemprop]").eachText().parallelStream()
                    .filter(text -> Pattern.compile(ISBN_REGEX).matcher(text).matches()).findAny()
                    .orElse(IsbnRetrieverStrategy.super.execute(url));
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
