package com.ghn1712.guiabolso.books.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;

public class ManningStrategy implements IsbnStrategy {

    @Override
    public String execute(String url) {
        final String defaultResponse = IsbnStrategy.super.execute(url);
        try {
            return Jsoup.connect(url).get().select("div.product-info ul li").eachText().parallelStream()
                    .filter(text -> text.split(" ")[0].equals("ISBN")).findAny().orElse(defaultResponse);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return defaultResponse;
    }
}
