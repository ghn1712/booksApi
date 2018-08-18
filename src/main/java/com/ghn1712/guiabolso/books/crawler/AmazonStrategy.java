package com.ghn1712.guiabolso.books.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;

public class AmazonStrategy implements IsbnRetrieverStrategy {

    @Override
    public String execute(String url) {
        final String defaultResponse = IsbnRetrieverStrategy.super.execute(url);
        try {
            return Jsoup.connect(url).get().select("div.content li").eachText().parallelStream()
                    .filter(text -> text.split(":")[0].equals("ISBN-13"))
                    .map(text -> text.split(":")[1].replaceAll("-", "")).findAny()
                    .orElse(defaultResponse);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return defaultResponse;
    }
}
