package com.ghn1712.guiabolso.books.gateways;

import java.io.IOException;
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
            Elements booksHtml = Jsoup.connect(crawlerConfig.getUrl()).get().select("article");
            List<String> booksTitles = booksHtml.select("h2").eachText().stream()
                    .map(title -> title.split(",")[0].toLowerCase()).collect(Collectors.toList());
            booksTitles.forEach(System.out::println);
            System.out.println("------------------------------");
            List<String> booksLanguage = booksHtml.select("div").eachText();
            booksLanguage.forEach(System.out::println);
            System.out.println("------------------------------");
            List<String> booksDescription = booksHtml.select("p:has(a)").select("a").stream().filter(
                    titleOnDescription -> booksTitles.contains(titleOnDescription.text().split("\\.")[0].toLowerCase()))
                    .map(filteredTitleOnDescription -> filteredTitleOnDescription.parent().text())
                    .collect(Collectors.toList());
            booksDescription.forEach(System.out::println);
            System.out.println("------------------------------");
            List<String> booksIsbn = booksHtml.select(".book-cover-image").parents().eachAttr("abs:href")
                    .parallelStream()
                    .map(this::getIsbn).collect(Collectors.toList());
            booksIsbn.forEach(System.out::println);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private Map<String, IsbnStrategy> loadStrategyMap() {
        HashMap<String, IsbnStrategy> map = new HashMap<>();
        map.put("amazon", new AmazonStrategy());
        return map;
    }

    private IsbnStrategy setStrategy(String url) {
        return strategyMap.get(url);
    }

    private String getIsbn(String url) {
        return IsbnContext.getIsbn(url, setStrategy(url));
    }
}
