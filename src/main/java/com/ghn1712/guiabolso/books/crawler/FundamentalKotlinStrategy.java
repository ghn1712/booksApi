package com.ghn1712.guiabolso.books.crawler;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.jsoup.Jsoup;

public class FundamentalKotlinStrategy implements IsbnRetrieverStrategy {

    @Override
    public String execute(String url) {
        try {
            return Jsoup.connect(url).get().select("div.scondary_content h2.dark-blue-text").eachText().parallelStream()
                    .filter(text -> text.split(" ")[0].equals("ISBN:")).map(text -> text.split(" ")[1]).findAny()
                    .orElse(IsbnRetrieverStrategy.super.execute(url));
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
