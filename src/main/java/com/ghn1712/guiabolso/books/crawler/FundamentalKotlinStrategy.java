package com.ghn1712.guiabolso.books.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;

public class FundamentalKotlinStrategy implements IsbnRetrieverStrategy {

    @Override
    public String execute(String url) {
        final String defaultResponse = IsbnRetrieverStrategy.super.execute(url);
        try {
            return Jsoup.connect(url).get().select("div.scondary_content h2.dark-blue-text").eachText().parallelStream()
                    .filter(text -> text.split(" ")[0].equals("ISBN:")).findAny()
                    .orElse(defaultResponse);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return defaultResponse;
    }
}
