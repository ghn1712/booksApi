package com.ghn1712.guiabolso.books.gateways;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.pmw.tinylog.Logger;

import com.ghn1712.guiabolso.books.config.CrawlerConfig;
import com.ghn1712.guiabolso.books.crawler.AmazonStrategy;
import com.ghn1712.guiabolso.books.crawler.FundamentalKotlinStrategy;
import com.ghn1712.guiabolso.books.crawler.IsbnRetrieverContext;
import com.ghn1712.guiabolso.books.crawler.IsbnRetrieverStrategy;
import com.ghn1712.guiabolso.books.crawler.KuramkitapStrategy;
import com.ghn1712.guiabolso.books.crawler.ManningStrategy;
import com.ghn1712.guiabolso.books.crawler.PacktpubStrategy;
import com.ghn1712.guiabolso.books.crawler.UnavailableStrategy;
import com.ghn1712.guiabolso.books.entities.Book;

public class BooksCrawlerGateway implements BooksListGateway {

    private CrawlerConfig crawlerConfig;
    private Map<String, IsbnRetrieverStrategy> strategyMap;

    @Inject
    public BooksCrawlerGateway(CrawlerConfig crawlerConfig) {
        this.crawlerConfig = crawlerConfig;
        strategyMap = loadStrategyMap();
    }

    @Override
    public List<Book> listBooks() {
        try {
            Elements booksHtml = Jsoup.connect(crawlerConfig.getUrl()).get().select("article");
            List<String> booksTitles = getBookTitles(booksHtml);
            List<String> booksLanguage = getBooksLanguage(booksHtml);
            List<String> booksDescription = getBooksDescription(booksHtml, booksTitles);
            List<String> booksIsbn = getBooksIsbn(booksHtml);
            return createBooksList(booksTitles, booksDescription, booksIsbn, booksLanguage);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private List<Book> createBooksList(List<String> booksTitles, List<String> booksDescription, List<String> booksIsbn,
            List<String> booksLanguages) {
        if (booksDescription.size() == booksTitles.size() && booksDescription.size() == booksIsbn.size()
                && booksDescription.size() == booksLanguages.size()) {
            List<Book> booksList = new ArrayList<>();
            for (int i = 0; i < booksDescription.size(); i++) {
                booksList.add(
                        new Book(booksTitles.get(i), booksDescription.get(i), booksIsbn.get(i), booksLanguages.get(i)));
            }
            return booksList;
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

    private Map<String, IsbnRetrieverStrategy> loadStrategyMap() {
        HashMap<String, IsbnRetrieverStrategy> map = new HashMap<>();
        map.put("amazon", new AmazonStrategy());
        map.put("manning", new ManningStrategy());
        map.put("packtpub", new PacktpubStrategy());
        map.put("fundamental-kotlin", new FundamentalKotlinStrategy());
        map.put("kuramkitap", new KuramkitapStrategy());
        return map;
    }

    private IsbnRetrieverStrategy setStrategy(String stringUrl) {
        try {
            String key = getKey(stringUrl);
            if (strategyMap.containsKey(key)) {
                return strategyMap.get(key);
            }
        }
        catch (MalformedURLException e) {
            Logger.warn(e.getMessage());
        }
        return new UnavailableStrategy();
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
        return IsbnRetrieverContext.getIsbn(url, setStrategy(url));
    }
}
