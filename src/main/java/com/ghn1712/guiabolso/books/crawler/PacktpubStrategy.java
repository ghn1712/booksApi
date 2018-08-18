package com.ghn1712.guiabolso.books.crawler;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

public class PacktpubStrategy implements IsbnRetrieverStrategy {

    private static final String ISBN_REGEX = "^[0-9]{13}$";

    @Override
    public String execute(String url) {
        final String defaultResponse = IsbnRetrieverStrategy.super.execute(url);
        try {
            return Jsoup.connect(url).get().select("div.book-info-isbn13 span[itemprop]").eachText().parallelStream()
                    .filter(text -> Pattern.compile(ISBN_REGEX).matcher(text).matches()).findAny()
                    .orElse(defaultResponse);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return defaultResponse;
    }
}
