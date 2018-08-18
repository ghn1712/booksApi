package com.ghn1712.guiabolso.books.crawler;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

public class KuramkitapStrategy implements IsbnRetrieverStrategy {

    private static final String REGEX = "^[0-9]{13}$";

    @Override
    public String execute(String url) {
        final String defaultResponse = IsbnRetrieverStrategy.super.execute(url);
        try {
            return Jsoup.connect(url).get().select("div.table-cell").eachText().parallelStream()
                    .filter(text -> Pattern.compile(REGEX).matcher(text).matches()).findAny()
                    .orElse(defaultResponse);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return defaultResponse;
    }
}
