package com.ghn1712.guiabolso.books.gateways;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.ghn1712.guiabolso.books.config.CrawlerConfig;
import com.ghn1712.guiabolso.books.crawler.IsbnRetriever;
import com.ghn1712.guiabolso.books.entities.Book;

public class BooksCrawlerGateway implements BooksListGateway {

    private CrawlerConfig crawlerConfig;
    private List<Book> booksList;

    @Inject
    public BooksCrawlerGateway(CrawlerConfig crawlerConfig) {
        this.crawlerConfig = crawlerConfig;
        booksList = new ArrayList<>();
        if (crawlerConfig.isStartupCacheOn()) {
            listBooks();
        }
    }

    @Override
    public List<Book> listBooks() {
        if ((crawlerConfig.isCacheOn() || crawlerConfig.isStartupCacheOn()) && !booksList.isEmpty()) {
            return booksList;
        }
        try {
            Elements booksHtml = Jsoup.connect(crawlerConfig.getUrl()).get().select("article");
            List<String> booksTitles = getBookTitles(booksHtml);
            List<String> booksLanguage = getBooksLanguage(booksHtml);
            List<String> booksDescription = getBooksDescription(booksHtml, booksTitles);
            List<String> booksIsbn = getBooksIsbn(booksHtml);
            booksList = createBooksList(booksTitles, booksDescription, booksIsbn, booksLanguage);
            return booksList;
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private List<Book> createBooksList(List<String> booksTitles, List<String> booksDescription, List<String> booksIsbn,
            List<String> booksLanguages) {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < booksDescription.size(); i++) {
            books.add(new Book(booksTitles.get(i), booksDescription.get(i), booksIsbn.get(i), booksLanguages.get(i)));
        }
        return books;
    }

    private List<String> getBooksIsbn(Elements booksHtml) {
        return booksHtml.select(".book-cover-image").parents().eachAttr("abs:href").parallelStream()
                .map(IsbnRetriever::getIsbn).collect(Collectors.toList());
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
        return booksHtml.select("h2").eachText().stream().map(title -> title.split(",")[0].toLowerCase())
                .collect(Collectors.toList());
    }
}
