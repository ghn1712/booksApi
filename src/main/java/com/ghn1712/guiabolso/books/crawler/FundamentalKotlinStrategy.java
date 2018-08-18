package com.ghn1712.guiabolso.books.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;

public class FundamentalKotlinStrategy implements IsbnRetrieverStrategy {

    @Override
    public String execute(String url) {
        try {
            return Jsoup.connect(url).get().select("div.scondary_content h2.dark-blue-text").eachText().parallelStream()
                    .filter(text -> text.split(" ")[0].equals("ISBN:")).findAny()
                    .orElse(IsbnRetrieverStrategy.super.execute(url));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return IsbnRetrieverStrategy.super.execute(url);
    }
}
