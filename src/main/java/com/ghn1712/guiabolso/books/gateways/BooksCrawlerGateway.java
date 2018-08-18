package com.ghn1712.guiabolso.books.gateways;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.ghn1712.guiabolso.books.config.CrawlerConfig;
import com.ghn1712.guiabolso.books.crawler.AmazonStrategy;
import com.ghn1712.guiabolso.books.crawler.IsbnContext;
import com.ghn1712.guiabolso.books.crawler.IsbnStrategy;
import com.ghn1712.guiabolso.books.crawler.UndefinedStrategy;
import com.ghn1712.guiabolso.books.entities.Book;

public class BooksCrawlerGateway implements BooksListGateway {

    private CrawlerConfig crawlerConfig;
    private Map<String, IsbnStrategy> strategyMap;

    @Inject
    public BooksCrawlerGateway(CrawlerConfig crawlerConfig) {
        this.crawlerConfig = crawlerConfig;
        strategyMap = loadStrategyMap();
    }

    @Override
    public List<Book> listBooks() {
        try {
            long start = System.currentTimeMillis();
            Elements booksHtml = Jsoup.connect(crawlerConfig.getUrl()).get().select("article");
            List<String> booksTitles = getBookTitles(booksHtml);
            List<String> booksLanguage = getBooksLanguage(booksHtml);
            List<String> booksDescription = getBooksDescription(booksHtml, booksTitles);
            List<String> booksIsbn = getBooksIsbn(booksHtml);
            booksIsbn.forEach(System.out::println);
            System.out.println(System.currentTimeMillis() - start);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<String> getBooksIsbn(Elements booksHtml) {
        return booksHtml.select(".book-cover-image").parents().eachAttr("abs:href")
                .parallelStream()
                .map(this::getIsbn).collect(Collectors.toList());
    }

    private List<String> getBooksDescription(Elements booksHtml, List<String> booksTitles) {
        return booksHtml.select("p:has(a)").select("a").stream().filter(
                titleOnDescription -> booksTitles.contains(titleOnDescription.text().split("\\.")[0].toLowerCase()))
                .map(filteredTitleOnDescription -> filteredTitleOnDescription.parent().text())
                .collect(Collectors.toList());
    }

    private List<String> getBooksLanguage(Elements booksHtml) {
        return booksHtml.select("div").eachText();
    }

    private List<String> getBookTitles(Elements booksHtml) {
        return booksHtml.select("h2").eachText().stream()
                .map(title -> title.split(",")[0].toLowerCase()).collect(Collectors.toList());
    }

    private Map<String, IsbnStrategy> loadStrategyMap() {
        HashMap<String, IsbnStrategy> map = new HashMap<>();
        map.put("amazon", new AmazonStrategy());
        return map;
    }

    private IsbnStrategy setStrategy(String stringUrl) {
        try {
            String key = getKey(stringUrl);
            if (strategyMap.containsKey(key)) {
                return strategyMap.get(key);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new UndefinedStrategy();
    }

    private String getKey(String stringUrl) throws MalformedURLException {
        String[] hostUrl = new URL(stringUrl).getHost().split("\\.");
        String key = hostUrl[0];
        if (key.equals("www")) {
            key = hostUrl[1];
        }
        return key;
    }

    private String getIsbn(String url) {
        return IsbnContext.getIsbn(url, setStrategy(url));
    }
}
