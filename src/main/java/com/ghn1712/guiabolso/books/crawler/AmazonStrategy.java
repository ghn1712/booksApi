package com.ghn1712.guiabolso.books.crawler;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.jsoup.Jsoup;

public class AmazonStrategy implements IsbnRetrieverStrategy {

    @Override
    public String execute(String url) {
        try {
            return Jsoup.connect(url).get().select("div.content li").eachText().parallelStream()
                    .filter(text -> text.split(" ")[0].equals("ISBN-13:"))
                    .map(text -> text.split(" ")[1].replaceAll("-", "")).findAny()
                    .orElse(IsbnRetrieverStrategy.super.execute(url));
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
